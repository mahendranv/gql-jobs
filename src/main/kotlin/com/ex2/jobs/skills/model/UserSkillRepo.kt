package com.ex2.jobs.skills.model

import org.springframework.data.jpa.repository.JpaRepository

interface UserSkillRepo : JpaRepository<UserSkillMap, String>