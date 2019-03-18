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
import techprague.nodes.dk.data.models.body.MatchById

interface MatchesApi {

    /**
     * GET /matches/{match_id}
     * Match data
     * @param matchId  (required)
     * @return Call&lt;Inline_response_200&gt;
     */
    @GET("/matches/{match_id}")
    fun matchesMatchIdGet(@retrofit2.http.Path("match_id") matchId: Int): Call<MatchById>
}
