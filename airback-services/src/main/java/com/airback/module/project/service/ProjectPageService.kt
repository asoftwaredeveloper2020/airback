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
package com.airback.module.project.service

import com.airback.db.persistence.service.IService
import com.airback.module.page.domain.Page

/**
 *
 * @author airback Ltd.
 * @since 4.5.6
 */
interface ProjectPageService : IService {
    /**
     *
     * @param page
     * @param createdUser
     * @param projectId
     * @param accountId
     */
    fun savePage(page: Page, createdUser: String, projectId: Int, accountId: Int)

    /**
     *
     * @param path
     * @param requestedUser
     * @return
     */
    fun getPage(path: String, requestedUser: String): Page?
}
