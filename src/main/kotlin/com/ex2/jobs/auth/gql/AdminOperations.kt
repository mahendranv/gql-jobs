package com.ex2.jobs.auth.gql

import com.ex2.jobs.adapters.RoleAdapter.toPoriRole
import com.ex2.jobs.auth.RoleService
import com.ex2.jobs.gen.DgsConstants
import com.ex2.jobs.gen.types.RoleInput
import com.ex2.jobs.security.AdminOnly
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument
import org.springframework.beans.factory.annotation.Autowired

@DgsComponent
class AdminOperations {

    @Autowired
    private lateinit var roleService: RoleService

    @AdminOnly
    @DgsData(
        parentType = DgsConstants.MUTATION.TYPE_NAME,
        field = DgsConstants.MUTATION.SetUserRole
    )
    fun setUserRole(@InputArgument("roleInput") roleInput: RoleInput): Boolean {
        roleService.assignRole(
            memberId = roleInput.memberId.toLong(),
            role = roleInput.role.toPoriRole()
        )
        return true
    }

}