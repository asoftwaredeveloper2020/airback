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
package com.airback.module.project.esb

import com.google.common.eventbus.AllowConcurrentEvents
import com.google.common.eventbus.Subscribe
import com.airback.common.ModuleNameConstants
import com.airback.common.dao.ActivityStreamMapper
import com.airback.common.dao.CommentMapper
import com.airback.common.dao.OptionValMapper
import com.airback.common.domain.ActivityStreamExample
import com.airback.common.domain.CommentExample
import com.airback.common.domain.OptionValExample
import com.airback.module.ecm.service.ResourceService
import com.airback.module.esb.GenericCommand
import com.airback.module.page.service.PageService
import org.springframework.stereotype.Component

/**
 * @author airback Ltd
 * @since 6.0.0
 */
@Component
class DeleteProjectCommand(private val activityStreamMapper: ActivityStreamMapper,
                           private val commentMapper: CommentMapper,
                           private val resourceService: ResourceService,
                           private val pageService: PageService,
                           private val optionValMapper: OptionValMapper) : GenericCommand() {

    @AllowConcurrentEvents
    @Subscribe
    fun removedProject(event: DeleteProjectEvent) {
        event.projects.forEach {
            deleteProjectActivityStream(it.id)
            deleteRelatedComments(it.id)
            deleteProjectFiles(event.accountId, it.id)
            deleteProjectPages(event.accountId, it.id)
            deleteProjectOptions(it.id)
        }
    }

    private fun deleteProjectActivityStream(projectId: Int) {
        val ex = ActivityStreamExample()
        ex.createCriteria().andExtratypeidEqualTo(projectId).andModuleEqualTo(ModuleNameConstants.PRJ)
        activityStreamMapper.deleteByExample(ex)
    }

    private fun deleteRelatedComments(projectId: Int) {
        val ex = CommentExample()
        ex.createCriteria().andExtratypeidEqualTo(projectId)
        commentMapper.deleteByExample(ex)
    }

    private fun deleteProjectFiles(accountId: Int, projectId: Int) {
        resourceService.removeResource("$accountId/project/$projectId", "", true, accountId)
    }

    private fun deleteProjectPages(accountId: Int, projectId: Int) {
        pageService.removeResource("$accountId/project/$projectId/.page")
    }

    private fun deleteProjectOptions(projectId: Int) {
        val ex = OptionValExample()
        ex.createCriteria().andExtraidEqualTo(projectId)
        optionValMapper.deleteByExample(ex)
    }
}