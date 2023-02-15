package com.qmk.musiccontroller.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.google.accompanist.pager.*
import com.qmk.music_controller.music_presentation.main.MusicScreen
import com.qmk.music_controller.playlist_presentation.main.PlaylistNavigation
import com.qmk.music_controller.setting_presentation.main.SettingsScreen
import com.qmk.musiccontroller.R
import kotlinx.coroutines.launch

typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var icon: Int, var title: String, var screen: ComposableFun) {
    object Playlists : TabItem(
        R.drawable.ic_playlist_24,
        "Playlists",
        { PlaylistNavigation() }
    )
    object Music : TabItem(
        R.drawable.ic_music_note_24,
        "Music",
        { MusicScreen() }
    )
    object Channels : TabItem(
        R.drawable.ic_subscriptions_24,
        "Channels",
        { Text(text = "Channels Screen") }
    )
    object Rules : TabItem(
        R.drawable.ic_balance_24,
        "Rules",
        { Text(text = "Naming Rules Screen") }
    )
    object Settings : TabItem(
        R.drawable.ic_settings_24,
        "Settings",
        { SettingsScreen() }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun MainScreen() {
    val tabs = listOf(
        TabItem.Playlists,
        TabItem.Music,
        TabItem.Channels,
        TabItem.Rules,
        TabItem.Settings
    )
    val pagerState = rememberPagerState()
    Scaffold(
        bottomBar = { Tabs(tabs = tabs, pagerState = pagerState) }
    ) {
        TabsContent(
            modifier = Modifier.padding(it),
            tabs = tabs,
            pagerState = pagerState
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<TabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, tabPositions))
        }
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                icon = { Icon(
                    painter = painterResource(id = tab.icon),
                    contentDescription = tab.title)},
                selected = pagerState.currentPage == index,
                onClick = { scope.launch { pagerState.animateScrollToPage(index) }},
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(
    modifier: Modifier = Modifier,
    tabs: List<TabItem>,
    pagerState: PagerState
) {
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        count = tabs.size,
        userScrollEnabled = false
    ) { page ->
        tabs[page].screen()
    }
}
