package com.ljyh.mei.ui.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import java.net.URLDecoder
import com.ljyh.mei.ui.screen.about.AboutScreen
import com.ljyh.mei.ui.screen.album.AlbumDetailScreen
import com.ljyh.mei.ui.screen.history.HistoryScreen
import com.ljyh.mei.ui.screen.local.LocalMusicScreen
import com.ljyh.mei.ui.screen.local.LocalSongListScreen
import com.ljyh.mei.ui.screen.main.home.HomeHubScreen
import com.ljyh.mei.ui.screen.main.library.LibraryScreen
import com.ljyh.mei.ui.screen.playlist.EveryDay
import com.ljyh.mei.ui.screen.playlist.PlaylistScreen
import com.ljyh.mei.ui.screen.search.SearchResultScreen
import com.ljyh.mei.ui.screen.setting.AppearanceSettings
import com.ljyh.mei.ui.screen.artist.ArtistScreen
import com.ljyh.mei.ui.screen.main.findmusic.FindMusicScreen
import com.ljyh.mei.ui.screen.setting.ContentsSetting
import com.ljyh.mei.ui.screen.setting.DownloadManageScreen
import com.ljyh.mei.ui.screen.setting.DownloadSetting
import com.ljyh.mei.ui.screen.setting.StorageManagementScreen
import com.ljyh.mei.ui.screen.setting.GeneralSettings
import com.ljyh.mei.ui.screen.setting.PlaySetting
import com.ljyh.mei.ui.screen.setting.EqualizerSettings
import com.ljyh.mei.ui.screen.setting.LyricsSettings
import com.ljyh.mei.ui.screen.setting.SettingScreen
import com.ljyh.mei.ui.screen.log.LogScreen
import com.ljyh.mei.ui.screen.comment.CommentScreen
import com.ljyh.mei.ui.screen.cloud.CloudMusicScreen
import com.ljyh.mei.ui.screen.podcast.PodcastDetailScreen
import com.ljyh.mei.ui.screen.podcast.PodcastScreen
import com.ljyh.mei.ui.screen.search.SearchLandingScreen
import com.ljyh.mei.ui.screen.social.ConversationScreen
import com.ljyh.mei.ui.screen.social.ConversationsScreen
import com.ljyh.mei.ui.screen.social.MessageContactsScreen
import com.ljyh.mei.ui.screen.listentogether.ListenTogetherScreen
import com.ljyh.mei.ui.screen.recognition.SongRecognitionScreen
import com.ljyh.mei.ui.screen.account.NeteaseLoginScreen
import com.ljyh.mei.ui.screen.account.AccountHomeScreen
import com.ljyh.mei.ui.screen.account.ListeningRankScreen
import com.ljyh.mei.ui.screen.song.SongWikiScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun navigationEntry(
    route: String,
    scrollBehavior: TopAppBarScrollBehavior,
    isNavigationTab: Boolean = false,
) {
    when {
        route == Screen.Home.route -> HomeHubScreen()
        route == Screen.Library.route -> LibraryScreen(isNavigationTab = isNavigationTab)
        route == Screen.FindMusic.route -> FindMusicScreen(isNavigationTab = isNavigationTab)
        route.startsWith("${Screen.PlaylistCategory.route}/") -> {
            val arguments = route.substringAfter("${Screen.PlaylistCategory.route}/")
                .split('/', limit = 2)
            if (arguments.size == 2) {
                val category = URLDecoder.decode(arguments[0], "UTF-8")
                val title = URLDecoder.decode(arguments[1], "UTF-8")
                FindMusicScreen(initialCategory = category, titleOverride = title)
            }
        }
        route == Screen.Podcasts.route -> PodcastScreen(isNavigationTab = isNavigationTab)
        route == Screen.CloudMusic.route -> CloudMusicScreen(isNavigationTab = isNavigationTab)
        route == Screen.Search.route -> SearchLandingScreen()
        route == Screen.PrivateMessages.route -> ConversationsScreen()
        route == Screen.MessageContacts.route -> MessageContactsScreen()
        route == Screen.ListenTogether.route -> ListenTogetherScreen()
        route == Screen.SongRecognition.route -> SongRecognitionScreen()
        route == Screen.NeteaseLogin.route -> NeteaseLoginScreen()
        route == Screen.AccountHome.route -> AccountHomeScreen()
        route == Screen.Setting.route -> SettingScreen(scrollBehavior)
        route == Screen.AppearanceSettings.route -> AppearanceSettings(scrollBehavior)
        route == Screen.GeneralSettings.route -> GeneralSettings()
        route == Screen.LyricsSettings.route -> LyricsSettings()
        route == Screen.ContentSettings.route -> ContentsSetting(scrollBehavior)
        route == Screen.PlaySettings.route -> PlaySetting(scrollBehavior)
        route == Screen.EqualizerSettings.route -> EqualizerSettings()
        route == Screen.DownloadSettings.route -> DownloadSetting(scrollBehavior)
        route == Screen.StorageManagement.route -> StorageManagementScreen()
        route == Screen.DownloadManage.route -> DownloadManageScreen(
            scrollBehavior = scrollBehavior,
            isNavigationTab = isNavigationTab,
        )
        route == Screen.LocalMusic.route -> LocalMusicScreen(scrollBehavior)
        route == Screen.EveryDay.route -> EveryDay()
        route == Screen.About.route -> AboutScreen()
        route == Screen.Log.route -> LogScreen()
        route == Screen.History.route -> HistoryScreen(isNavigationTab = isNavigationTab)
        route.startsWith("${Screen.PrivateConversation.route}/") -> {
            route.substringAfter("${Screen.PrivateConversation.route}/").toLongOrNull()
                ?.let { userId -> ConversationScreen(userId) }
        }
        route.startsWith("${Screen.AccountListeningRank.route}/") -> {
            route.substringAfter("${Screen.AccountListeningRank.route}/").toLongOrNull()
                ?.let { userId -> ListeningRankScreen(userId) }
        }
        route.startsWith("${Screen.PodcastDetail.route}/") -> {
            route.substringAfter("${Screen.PodcastDetail.route}/").toLongOrNull()
                ?.let { id -> PodcastDetailScreen(id) }
        }
        route.startsWith("${Screen.LocalSongList.route}/") -> {
            val args = route.substringAfter("${Screen.LocalSongList.route}/").split('/', limit = 2)
            if (args.size == 2) {
                val type = args[0]
                val name = args[1]
                val filterValue: String
                val title: String
                when (type) {
                    "folder" -> {
                        filterValue = URLDecoder.decode(name, "UTF-8")
                        title = filterValue.substringAfterLast('/').ifEmpty {
                            filterValue.substringAfterLast(":")
                        }
                    }
                    "artist", "album" -> {
                        filterValue = name
                        title = name
                    }
                    else -> {
                        filterValue = name
                        title = stringResource(com.ljyh.mei.R.string.local_music_all_songs)
                    }
                }
                LocalSongListScreen(
                    filterType = if (type == "folder") "folder" else type,
                    filterValue = filterValue,
                    title = title,
                    scrollBehavior = scrollBehavior,
                )
            }
        }
        route.startsWith("${Screen.SearchResult.route}/") -> {
            val args = route.substringAfter("${Screen.SearchResult.route}/").split('/', limit = 2)
            if (args.size == 2) {
                SearchResultScreen(
                    query = android.net.Uri.decode(args[0]),
                    type = args[1].toIntOrNull() ?: 1,
                )
            }
        }
        route.startsWith("${Screen.PlayList.route}/") -> {
            route.substringAfter("${Screen.PlayList.route}/").toLongOrNull()
                ?.let { PlaylistScreen(id = it) }
        }
        route.startsWith("${Screen.Album.route}/") -> {
            route.substringAfter("${Screen.Album.route}/").toLongOrNull()
                ?.let { AlbumDetailScreen(id = it) }
        }
        route.startsWith("${Screen.Artist.route}/") -> {
            route.substringAfter("${Screen.Artist.route}/")
                .let { ArtistScreen(id = it) }
        }
        route.startsWith("${Screen.Comment.route}/") -> {
            route.substringAfter("${Screen.Comment.route}/")
                .let { CommentScreen(songId = it) }
        }
        route.startsWith("${Screen.SongWiki.route}/") -> {
            route.substringAfter("${Screen.SongWiki.route}/").toLongOrNull()
                ?.let { SongWikiScreen(songId = it) }
        }
        else -> error("Unknown navigation route: $route")
    }
}
