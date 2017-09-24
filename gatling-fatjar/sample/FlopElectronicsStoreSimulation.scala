package com.hybris.gatling.performance.accelerator.electronics

import scala.concurrent.duration.DurationInt
import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.validation._
import com.hybris.gatling.performance.accelerator.electronics.config.Config
import io.gatling.core.scenario.Scenario
import com.hybris.gatling.performance.accelerator.electronics.request.BrowseHomepage
import com.hybris.gatling.performance.accelerator.electronics.request.BrowseCategory
import com.hybris.gatling.performance.accelerator.electronics.request.Search
import com.hybris.gatling.performance.accelerator.electronics.request.BrowseProduct
import scala.util.Random
import com.hybris.gatling.performance.accelerator.electronics.request.AddToCart
import com.hybris.gatling.performance.accelerator.electronics.request.Checkout

/**
 * This is electronics store simulation for hybris.
 */
class FlopElectronicsStoreSimulation extends Simulation {

  val scn = scenario("Electronics Store Load Test Scenario")
    .feed(csv("data/users.csv").random)
    .exec(BrowseHomepage.chain)
    .repeat(Config.REPEAT_COUNT) {
      randomSwitch(
        Config.BROWSE_CATEGORY_RATIO -> BrowseCategory.chain,
        Config.SEARCH_RATIO -> Search.chain,
        Config.BROWSE_PRODUCT_RATIO -> BrowseProduct.chain)
//      .randomSwitch(
//        Config.ADD_TO_CART_RATIO -> AddToCart.chain)
    }
//  	.randomSwitch(
//  		Config.CHECKOUT_RATIO -> Checkout.chain)

  setUp(scn.inject(constantUsersPerSec(Config.USER_PER_SEC) during (Config.DURATION seconds)))
//  setUp(
//        scn.inject(
//                    nothingFor(10 seconds),
//                    atOnceUsers(10),
//                    rampUsers(50) over(50 seconds),
//                    constantUsersPerSec(100) during (100 seconds)
//                  )
//          scn.inject(  ramp(Config.RAMP_UP_USERS users) over (Config.RAMP_UP_DURATION seconds),
//                       constantRate(Config.USER_PER_SEC usersPerSec) during (Config.DURATION seconds)
//                    )
//          )
            .protocols(Config.HTTP_PROTOCOL)
}