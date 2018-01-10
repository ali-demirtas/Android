/*
 * Copyright (c) 2017 DuckDuckGo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duckduckgo.app.job

import com.duckduckgo.app.httpsupgrade.api.HttpsUpgradeListDownloader
import com.duckduckgo.app.trackerdetection.Client
import com.duckduckgo.app.trackerdetection.api.TrackerDataDownloader
import io.reactivex.Completable
import javax.inject.Inject

class AppConfigurationDownloader @Inject constructor(
        private val trackerDataDownloader: TrackerDataDownloader,
        private val httpsUpgradeListDownloader: HttpsUpgradeListDownloader
) {

    fun downloadTask(): Completable {
        val easyListDownload = trackerDataDownloader.downloadList(Client.ClientName.EASYLIST)
        val easyPrivacyDownload = trackerDataDownloader.downloadList(Client.ClientName.EASYPRIVACY)
        val disconnectDownload = trackerDataDownloader.downloadList(Client.ClientName.DISCONNECT)
        val httpsUpgradeDownload = httpsUpgradeListDownloader.downloadList()

        return Completable.merge(mutableListOf(easyListDownload, easyPrivacyDownload, disconnectDownload, httpsUpgradeDownload))
    }
}