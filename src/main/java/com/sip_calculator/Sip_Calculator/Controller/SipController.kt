package com.sip_calculator.Sip_Calculator.Controller

import com.sip_calculator.Sip_Calculator.Dto.InvestmentDetailsDto
import com.sip_calculator.Sip_Calculator.Dto.YearlyInvestment
import com.sip_calculator.Sip_Calculator.Dto.YearlyInvestments
import com.sip_calculator.Sip_Calculator.RateLimiter.WithRateLimitProtection
import com.sip_calculator.Sip_Calculator.Services.SipCalculationService
import com.sip_calculator.Sip_Calculator.Util.CustomUtils
import com.sip_calculator.Sip_Calculator.Util.FileUtils
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.math.BigDecimal
import java.nio.file.Path
import java.nio.file.Paths

@RestController()
@RequestMapping("/")
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class SipController(
    private val fileUtils : FileUtils,
    private val sipCalculationService: SipCalculationService,
    @Value("\${enable.lumpsum}") private var enableLumpSum: Boolean
) {
//    @WithRateLimitProtection
//    @PostMapping("/getInvestment")
//    fun getInvestementDetails(
//        @RequestBody investmentDetails : InvestmentDetailsDto
//    ):Mono<YearlyInvestments>{
//        val returnList = mutableListOf<YearlyInvestment>()
//
//        enableLumpSum = investmentDetails.lumpsumdata?.isNotEmpty()?: false
//
//        val investmentFlux = Flux.range(investmentDetails.startingYear, investmentDetails.numberOfYears)
//            .flatMap { year ->
//                val currentYearInvestment = YearlyInvestment(currentYear = year)
//                val lastInvestment = returnList.lastOrNull()
//
//                sipCalculationService.calculation(investmentDetails, currentYearInvestment,lastInvestment,investmentDetails.lumpsumdata)
//
//                CustomUtils.setDecimalToTwoPlaces(currentYearInvestment)
//                returnList.add(currentYearInvestment)
//
//                Mono.just(currentYearInvestment)
//            }
//        return investmentFlux.collectList().map { YearlyInvestments(it)}
//    }

    @WithRateLimitProtection
    @PostMapping("/getInvestment")
    fun getInvestementDetails(
        @RequestBody investmentDetails: InvestmentDetailsDto
    ): Mono<YearlyInvestments> {
        val returnList = mutableListOf<YearlyInvestment>()

        // Check if this is a lumpsum-only calculation
        return if (investmentDetails.investedTime == 0) {
            Flux.range(0, investmentDetails.numberOfYears)
                .concatMap { yearOffset ->
                    val currentYear = investmentDetails.startingYear + yearOffset
                    val currentYearInvestment = YearlyInvestment(currentYear = currentYear)
                    val lastInvestment = returnList.lastOrNull()

                    sipCalculationService.calculationForJustLumpSum(
                        investmentDetails,
                        currentYearInvestment,
                        lastInvestment,
                        returnList
                    ).thenReturn(currentYearInvestment)
                }
                .collectList()
                .map { YearlyInvestments(it) }
        } else {
            // Regular SIP calculation with potential lumpsum
            val isLumpSumEnabled = enableLumpSum &&
                    (investmentDetails.lumpsumdata?.isNotEmpty() == true)

            Flux.range(0, investmentDetails.numberOfYears)
                .concatMap { yearOffset ->
                    val currentYear = investmentDetails.startingYear + yearOffset
                    val currentYearInvestment = YearlyInvestment(currentYear = currentYear)
                    val lastInvestment = returnList.lastOrNull()

                    sipCalculationService.calculation(
                        investmentDetails,
                        currentYearInvestment,
                        lastInvestment,
                        investmentDetails.lumpsumdata ?: emptyList()
                    ).thenReturn(currentYearInvestment)
                        .doOnNext { investment ->
                            CustomUtils.setDecimalToTwoPlaces(investment)
                            returnList.add(investment)
                        }
                }
                .collectList()
                .map { YearlyInvestments(it) }
        }
    }

    @WithRateLimitProtection
    @PostMapping("/getLumpSumAmount")
    fun getInvestmentDetailsForLumpSum(@RequestBody investmentDetails: InvestmentDetailsDto): Mono<YearlyInvestments> {
        val returnList = mutableListOf<YearlyInvestment>()

        val investmentFlux = Flux.range(investmentDetails.startingYear, investmentDetails.numberOfYears)
            .flatMap { year ->
                val currentYearInvestment = YearlyInvestment(currentYear = year).apply {
                    steppedUpSipValue = BigDecimal.valueOf(investmentDetails.monthlySipAmt.toDouble())
                    overAllInvested = BigDecimal.valueOf(investmentDetails.monthlySipAmt.toDouble())
                }

                val lastInvestment = returnList.lastOrNull()

                sipCalculationService.calculationForJustLumpSum(investmentDetails, currentYearInvestment, lastInvestment, returnList)

                returnList.add(currentYearInvestment)
                Mono.just(currentYearInvestment)
            }

        return investmentFlux.collectList().map { YearlyInvestments(it) }
    }

    @WithRateLimitProtection
    @PostMapping("/downloadFile")
    fun downloadFile(@RequestBody fileDto: YearlyInvestments): Mono<ResponseEntity<Resource>> {
        return fileUtils.createCsvAndAddOrdering(fileDto.yearlyInvestments) // Returns Mono<String>
            .flatMap { path -> // Unwraps Mono<String>
                if (path.isNotEmpty()) { // Now `path` is a String, so no type mismatch
                    val filePath: Path = Paths.get(path)
                    val resource: Resource = InputStreamResource(FileInputStream(filePath.toFile()))
                    val headers = HttpHeaders().apply {
                        add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${filePath.fileName}")
                    }
                    Mono.just(ResponseEntity.ok().headers(headers).body(resource)) // Wrap in Mono
                } else {
                    Mono.empty() // Return empty if path is empty
                }
            }
    }
}