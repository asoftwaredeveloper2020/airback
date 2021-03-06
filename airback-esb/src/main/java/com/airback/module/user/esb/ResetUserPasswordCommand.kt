/**
 * Copyright © airback
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.airback.module.user.esb

import com.google.common.eventbus.AllowConcurrentEvents
import com.google.common.eventbus.Subscribe
import com.airback.common.UrlEncodeDecoder
import com.airback.common.domain.MailRecipientField
import com.airback.common.i18n.MailI18nEnum
import com.airback.configuration.ApplicationConfiguration
import com.airback.configuration.IDeploymentMode
import com.airback.core.utils.DateTimeUtils
import com.airback.i18n.LocalizationHelper
import com.airback.module.esb.GenericCommand
import com.airback.module.mail.service.ExtMailService
import com.airback.module.mail.service.IContentGenerator
import com.airback.module.user.accountsettings.localization.UserI18nEnum
import com.airback.module.user.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * @author airback Ltd
 * @since 6.0.0
 */
@Component
class ResetUserPasswordCommand(private val extMailService: ExtMailService,
                               private val userService: UserService,
                               private val contentGenerator: IContentGenerator,
                               private val deploymentMode: IDeploymentMode,
                               private val applicationConfiguration: ApplicationConfiguration) : GenericCommand() {
    companion object {
        val LOG = LoggerFactory.getLogger(ResetUserPasswordCommand::class.java)
    }

    @AllowConcurrentEvents
    @Subscribe
    fun execute(event: RequestToResetPasswordEvent) {
        val username = event.username
        val user = userService.findUserByUserName(username)
        if (user != null) {
            val subDomain = "api"
            val recoveryPasswordURL = "${deploymentMode.getSiteUrl(subDomain)}user/recoverypassword/${UrlEncodeDecoder.encode(username)}"
            val locale = LocalizationHelper.getLocaleInstance(user.language)
            contentGenerator.putVariable("username", user.username)
            contentGenerator.putVariable("urlRecoveryPassword", recoveryPasswordURL)
            contentGenerator.putVariable("copyRight", LocalizationHelper.getMessage(locale, MailI18nEnum.Copyright,
                    DateTimeUtils.getCurrentYear()))
            val recipient = MailRecipientField(user.email, user.username)
            val recipientFields = listOf(recipient)

            extMailService.sendHTMLMail(applicationConfiguration.notifyEmail, applicationConfiguration.siteName,
                    recipientFields,
                    LocalizationHelper.getMessage(locale, UserI18nEnum.MAIL_RECOVERY_PASSWORD_SUBJECT,
                            applicationConfiguration.siteName),
                    contentGenerator.parseFile("mailUserRecoveryPasswordNotifier.ftl", locale))
        } else {
            LOG.error("Can not reset the password of username $username because this user is not existed")
        }
    }
}