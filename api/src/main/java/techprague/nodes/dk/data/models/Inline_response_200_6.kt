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
package techprague.nodes.dk.data.models

/**
 *
 * @param account_id account_id
 * @param last_played last_played
 * @param win win
 * @param games games
 * @param with_win with_win
 * @param with_games with_games
 * @param against_win against_win
 * @param against_games against_games
 * @param with_gpm_sum with_gpm_sum
 * @param with_xpm_sum with_xpm_sum
 * @param personaname personaname
 * @param name name
 * @param is_contributor is_contributor
 * @param last_login last_login
 * @param avatar avatar
 * @param avatarfull avatarfull
 */
data class Inline_response_200_6(
    /* account_id */
    val account_id: Int? = null,

    /* last_played */
    val last_played: Int? = null,

    /* win */
    val win: Int? = null,

    /* games */
    val games: Int? = null,

    /* with_win */
    val with_win: Int? = null,

    /* with_games */
    val with_games: Int? = null,

    /* against_win */
    val against_win: Int? = null,

    /* against_games */
    val against_games: Int? = null,

    /* with_gpm_sum */
    val with_gpm_sum: Int? = null,

    /* with_xpm_sum */
    val with_xpm_sum: Int? = null,

    /* personaname */
    val personaname: String? = null,

    /* name */
    val name: String? = null,

    /* is_contributor */
    val is_contributor: Boolean? = null,

    /* last_login */
    val last_login: String? = null,

    /* avatar */
    val avatar: String? = null,

    /* avatarfull */
    val avatarfull: String? = null
)