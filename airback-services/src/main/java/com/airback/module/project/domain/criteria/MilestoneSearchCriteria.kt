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
package com.airback.module.project.domain.criteria

import com.airback.db.arguments.NumberSearchField
import com.airback.db.arguments.SearchCriteria
import com.airback.db.arguments.SetSearchField
import com.airback.db.arguments.StringSearchField

/**
 * @author airback Ltd.
 * @since 1.0
 */
class MilestoneSearchCriteria(var assignUser: StringSearchField? = null,
                              var statuses: SetSearchField<String>? = null,
                              var projectIds: SetSearchField<Int>? = null,
                              var id: NumberSearchField? = null,
                              var milestoneName: StringSearchField? = null) : SearchCriteria()
