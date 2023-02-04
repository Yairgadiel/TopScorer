package com.example.topscorer.ui.games.composables

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.topscorer.R
import com.example.topscorer.data.model.Fixture
import com.example.topscorer.data.model.Game
import com.example.topscorer.data.model.League
import com.example.topscorer.data.model.Page
import com.example.topscorer.data.model.PrimeStory
import com.example.topscorer.data.model.Status
import com.example.topscorer.data.model.Team
import com.example.topscorer.data.model.Teams
import com.example.topscorer.data.model.Venue
import com.example.topscorer.data.model.WscGame
import com.example.topscorer.util.toSimpleDate

@Composable
fun GameItem(game: Game, onGameClick : (PrimeStory) -> Unit) {

    val lastPage = game.wscGame?.primeStory?.pages?.last()

    requireNotNull(lastPage) {
        game.wscGame?.name ?: "null game"
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onGameClick(game.wscGame.primeStory) }
    ) {
        Image(
            painter = painterResource(id = R.drawable.field),
            contentDescription = "field",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(4.dp)),
            alpha = 0.4f,
            contentScale = ContentScale.FillBounds
        )

        Column(modifier = Modifier
            .padding(8.dp)
            .align(Alignment.BottomStart)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                TeamItem(modifier = Modifier.weight(1f), game.teams.home)

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = stringResource(
                        R.string.score_template,
                        lastPage.homeScore,
                        lastPage.awayScore
                    ),
                    style = TextStyle(
                        fontSize = 56.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        shadow = Shadow(color = Color.DarkGray, blurRadius = 5f),
                        fontFamily = FontFamily.Serif
                    ),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                TeamItem(modifier = Modifier.weight(1f), game.teams.away)
            }


            Row {
                Text(
                    text = (game.fixture.timestamp * 1000L).toSimpleDate(),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = game.league.name,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row {
                Text(
                    text = game.fixture.venue.name,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun TeamItem(modifier: Modifier = Modifier, team: Team) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(model = team.logo, contentDescription = team.name)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = team.name,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            ),
            modifier = Modifier
                .height(50.dp)
                .width(135.dp)
        )
    }
}

@Preview
@Composable
fun GamePreview() {
    val game = Game(
        WSCGameId = "11", fixture = Fixture(
            date = "2023-01-23T20:00:00+00:00",
            id = 0,
            referee = "JesÃºs Gil",
            status = Status(elapsed = null, long = "", short = ""),
            timestamp = 1674504000,
            timezone = "UTC",
            venue = Venue(city = "Valencia", id = 0, name = "Estadio de Mestalla")
        ), league = League(
            country = "Spain",
            flag = "https://media-3.api-sports.io/flags/es.svg",
            id = 111,
            logo = "https://media.api-sports.io/football/leagues/140.png",
            name = "La Liga",
            round = "Regular Season - 18",
            season = 2022
        ), storifyMeHandle = "", storifyMeID = 0, teams = Teams(
            away = Team(
                id = 0,
                logo = "https://media-3.api-sports.io/football/teams/723.png",
                name = "Almeria",
                winner = null
            ), home = Team(id = 0, logo = "https://media.api-sports.io/football/teams/532.png", name = "Valencia", winner = null)
        ), wscGame = WscGame(
            awayTeamName = "Almeria",
            gameId = "",
            gameTime = "2023-01-23T20:00:00",
            homeTeamName = "Valencia",
            name = "Valencia vs Almeria",
            primeStory = PrimeStory(
                pages = listOf(Page(
                    actionType = "Shot",
                    awayScore = 2,
                    duration = 18520,
                    eventType = "PlayByPlay",
                    gameClock = "95:44",
                    homeScore = 2,
                    paggeId = "",
                    period = "2",
                    rating = 3,
                    title = "Shot by Samuel",
                    videoUrl = "https://wsczoominwestus.prod-cdn.clipro.tv/editor/4c60c364-9cd1-4afc-9ddb-8e5df843a7ef.mp4"
                )),
                publishDate = "",
                storyId = "",
                storyThumbnail = "",
                storyThumbnailSquare = "",
                title = ""
            )
        )
    )
    GameItem(game = game) {}
}