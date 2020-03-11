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
package com.airback.module.project.schedule.email.service

import com.hp.gagawa.java.elements.A
import com.hp.gagawa.java.elements.Span
import com.airback.common.MonitorTypeConstants
import com.airback.common.i18n.GenericI18Enum
import com.airback.common.i18n.OptionI18nEnum
import com.airback.core.airbackException
import com.airback.core.utils.StringUtils
import com.airback.html.FormatUtils
import com.airback.html.LinkUtils
import com.airback.module.mail.MailUtils
import com.airback.module.project.ProjectLinkGenerator
import com.airback.module.project.ProjectTypeConstants
import com.airback.module.project.domain.ProjectRelayEmailNotification
import com.airback.module.project.i18n.ComponentI18nEnum
import com.airback.module.project.domain.Component.Field
import com.airback.module.project.domain.SimpleComponent
import com.airback.module.project.service.ComponentService
import com.airback.module.user.AccountLinkGenerator
import com.airback.module.user.service.UserService
import com.airback.schedule.email.ItemFieldMapper
import com.airback.schedule.email.MailContext
import com.airback.schedule.email.format.FieldFormat
import com.airback.schedule.email.format.I18nFieldFormat
import com.airback.schedule.email.project.ComponentRelayEmailNotificationAction
import com.airback.spring.AppContextUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

/**
 * @author airback Ltd
 * @since 6.0.0
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
class ComponentRelayEmailNotificationActionImpl : SendMailToAllMembersAction<SimpleComponent>(), ComponentRelayEmailNotificationAction {
    @Autowired private lateinit var componentService: ComponentService
    private val mapper = ComponentFieldNameMapper()

    override fun buildExtraTemplateVariables(context: MailContext<SimpleComponent>) {
        val emailNotification = context.emailNotification

        val summary = bean!!.name
        val summaryLink = ProjectLinkGenerator.generateComponentPreviewFullLink(siteUrl, bean!!.projectid, bean!!.id)

        val avatarId = if (projectMember != null) projectMember!!.memberAvatarId else ""
        val userAvatar = LinkUtils.newAvatar(avatarId)

        val makeChangeUser = "${userAvatar.write()} ${emailNotification.changeByUserFullName}"
        val actionEnum = when (emailNotification.action) {
            MonitorTypeConstants.CREATE_ACTION -> ComponentI18nEnum.MAIL_CREATE_ITEM_HEADING
            MonitorTypeConstants.UPDATE_ACTION -> ComponentI18nEnum.MAIL_UPDATE_ITEM_HEADING
            MonitorTypeConstants.ADD_COMMENT_ACTION -> ComponentI18nEnum.MAIL_COMMENT_ITEM_HEADING
            else -> throw airbackException("Not support action ${emailNotification.action}")
        }

        contentGenerator.putVariable("projectName", bean!!.projectName)
        contentGenerator.putVariable("projectNotificationUrl", ProjectLinkGenerator.generateProjectSettingFullLink(siteUrl, bean!!.projectid))
        contentGenerator.putVariable("actionHeading", context.getMessage(actionEnum, makeChangeUser))
        contentGenerator.putVariable("name", summary)
        contentGenerator.putVariable("summaryLink", summaryLink)
    }

    override fun getBeanInContext(notification: ProjectRelayEmailNotification): SimpleComponent? =
            componentService.findById(notification.typeid.toInt(), notification.saccountid)

    override fun getItemName(): String = StringUtils.trim(bean!!.description, 100)

    override fun getProjectName(): String = bean!!.projectName

    override fun getCreateSubject(context: MailContext<SimpleComponent>): String = context.getMessage(
            ComponentI18nEnum.MAIL_CREATE_ITEM_SUBJECT, bean!!.projectName, context.changeByUserFullName, getItemName())

    override fun getCreateSubjectNotification(context: MailContext<SimpleComponent>): String = context.getMessage(
            ComponentI18nEnum.MAIL_CREATE_ITEM_SUBJECT, projectLink(), userLink(context), componentLink())

    override fun getUpdateSubject(context: MailContext<SimpleComponent>): String = context.getMessage(
            ComponentI18nEnum.MAIL_UPDATE_ITEM_SUBJECT, bean!!.projectName, context.changeByUserFullName, getItemName())

    override fun getUpdateSubjectNotification(context: MailContext<SimpleComponent>): String = context.getMessage(
            ComponentI18nEnum.MAIL_UPDATE_ITEM_SUBJECT, projectLink(), userLink(context), componentLink())

    override fun getCommentSubject(context: MailContext<SimpleComponent>): String = context.getMessage(
            ComponentI18nEnum.MAIL_COMMENT_ITEM_SUBJECT, bean!!.projectName, context.changeByUserFullName, getItemName())

    override fun getCommentSubjectNotification(context: MailContext<SimpleComponent>): String = context.getMessage(
            ComponentI18nEnum.MAIL_COMMENT_ITEM_SUBJECT, projectLink(), userLink(context), componentLink())

    private fun projectLink() = A(ProjectLinkGenerator.generateProjectLink(bean!!.projectid)).appendText(bean!!.projectName).write()

    private fun userLink(context: MailContext<SimpleComponent>) = A(AccountLinkGenerator.generateUserLink(context.user.username)).appendText(context.changeByUserFullName).write()

    private fun componentLink() = A(ProjectLinkGenerator.generateComponentPreviewLink(bean!!.projectid, bean!!.id)).appendText(getItemName()).write()

    override fun getItemFieldMapper(): ItemFieldMapper = mapper

    override fun getType(): String = ProjectTypeConstants.COMPONENT

    override fun getTypeId(): String = "${bean!!.id}"

    class ComponentFieldNameMapper : ItemFieldMapper() {
        init {
            put(Field.description, GenericI18Enum.FORM_DESCRIPTION, true)
            put(Field.status, I18nFieldFormat(Field.status.name, GenericI18Enum.FORM_STATUS,
                    OptionI18nEnum.StatusI18nEnum::class.java))
            put(Field.userlead, LeadFieldFormat(Field.userlead.name, ComponentI18nEnum.FORM_LEAD))
        }
    }

    class LeadFieldFormat(fieldName: String, displayName: Enum<*>) : FieldFormat(fieldName, displayName) {
        override fun formatField(context: MailContext<*>): String {
            val component = context.wrappedBean as SimpleComponent
            return if (component.userlead != null) {
                val userAvatarLink = MailUtils.getAvatarLink(component.userLeadAvatarId, 16)
                val img = FormatUtils.newImg("avatar", userAvatarLink)
                val userLink = AccountLinkGenerator.generatePreviewFullUserLink(MailUtils.getSiteUrl(component.saccountid),
                        component.userlead)
                val link = FormatUtils.newA(userLink, component.userLeadFullName!!)
                FormatUtils.newLink(img, link).write()
            } else Span().write()
        }

        override fun formatField(context: MailContext<*>, value: String): String {
            if (StringUtils.isBlank(value)) {
                return Span().write()
            }
            val userService = AppContextUtil.getSpringBean(UserService::class.java)
            val user = userService.findUserByUserNameInAccount(value, context.saccountid)
            return if (user != null) {
                val userAvatarLink = MailUtils.getAvatarLink(user.avatarid, 16)
                val userLink = AccountLinkGenerator.generatePreviewFullUserLink(MailUtils.getSiteUrl(context.saccountid),
                        user.username)
                val img = FormatUtils.newImg("avatar", userAvatarLink)
                val link = FormatUtils.newA(userLink, user.displayName!!)
                FormatUtils.newLink(img, link).write()
            } else value
        }
    }

}