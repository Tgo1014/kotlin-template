/**
 * OpenDota API
 * # Introduction The OpenDota API provides Dota 2 related data including advanced match data extracted from match replays.  **Beginning 2018-04-22, the OpenDota API is limited to 50,000 free calls per month and 60 requests/minute** We offer a Premium Tier with unlimited API calls and higher rate limits. Check out the [API page](https://www.opendota.com/api-keys) to learn more.
 *
 * OpenAPI spec version: 17.6.1
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
package techprague.nodes.dk.data.apis

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ScenariosApi {

    /**
     * GET /scenarios/itemTimings
     * Win rates for certain item timings on a hero for items that cost at least 1400 gold
     * @param item Filter by item name e.g. \&quot;spirit_vessel\&quot; (optional)
     * @param heroId Hero ID (optional)
     * @return Call&lt;Array&lt;Any&gt;&gt;
     */
    @GET("scenarios/itemTimings")
    fun scenariosItemTimingsGet(
        @Query("item") item: String,
        @Query("hero_id") heroId: Int
    ): Call<Array<Any>>

    /**
     * GET /scenarios/laneRoles
     * Win rates for heroes in certain lane roles
     * @param laneRole Filter by lane role 1-4 (Safe, Mid, Off, Jungle) (optional)
     * @param heroId Hero ID (optional)
     * @return Call&lt;Array&lt;Any&gt;&gt;
     */
    @GET("scenarios/laneRoles")
    fun scenariosLaneRolesGet(
        @Query("lane_role") laneRole: String,
        @Query("hero_id") heroId: Int
    ): Call<Array<Any>>

    /**
     * GET /scenarios/misc
     * Miscellaneous team scenarios
     * @param scenario pos_chat_1min,neg_chat_1min,courier_kill,first_blood (optional)
     * @return Call&lt;Array&lt;Any&gt;&gt;
     */
    @GET("scenarios/misc")
    fun scenariosMiscGet(@Query("scenario") scenario: String): Call<Array<Any>>
}