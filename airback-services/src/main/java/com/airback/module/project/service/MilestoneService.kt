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
 * along with this program.  If not, see <http:></http:>//www.gnu.org/licenses/>.
 */
package com.airback.module.project.service

import com.airback.core.cache.CacheEvict
import com.airback.core.cache.CacheKey
import com.airback.core.cache.Cacheable
import com.airback.db.persistence.service.IDefaultService
import com.airback.module.project.domain.Milestone
import com.airback.module.project.domain.SimpleMilestone
import com.airback.module.project.domain.criteria.MilestoneSearchCriteria

/**
 * @author airback Ltd.
 * @since 1.0.0
 */
interface MilestoneService : IDefaultService<Int, Milestone, MilestoneSearchCriteria> {

    @Cacheable
    fun findById(milestoneId: Int, @CacheKey sAccountId: Int): SimpleMilestone?

    @CacheEvict
    fun massUpdateOptionIndexes(mapIndexes: List<Map<String, Int>>, @CacheKey sAccountId: Int)
}
