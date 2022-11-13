package com.qmk.musiccontroller.domain.controller

import android.util.Log
import com.qmk.musiccontroller.data.RetrofitHelper
import com.qmk.musiccontroller.data.api.NamingRulesAPI
import com.qmk.musiccontroller.data.model.NamingRuleInfo
import com.qmk.musiccontroller.domain.model.NamingRuleModel
import retrofit2.awaitResponse

class NamingRuleController {
    private val tag = "NamingRuleController"
    private val namingRulesAPI = RetrofitHelper.getInstance().create(NamingRulesAPI::class.java)

    suspend fun getNamingRules(): List<NamingRuleModel> {
        val namingRules = namingRulesAPI.getNamingRules().body()?.map {
            NamingRuleModel(it.id, it.replace, it.replaceBy, it.priority)
        }
        Log.i(tag, namingRules.toString())
        return namingRules ?: emptyList()
    }

    suspend fun addNamingRule(replace: String, replaceBy: String, priority: Int): NamingRuleModel? {
        val newNamingRule = namingRulesAPI.postNamingRules(NamingRuleInfo(
            replace = replace,
            replaceBy = replaceBy,
            priority = priority
        )).awaitResponse().body()
        Log.i(tag, "New rule : $newNamingRule")
        return newNamingRule?.let { NamingRuleModel(it) }
    }

    suspend fun getNamingRule(id: String): NamingRuleModel? {
        val namingRuleInfo = namingRulesAPI.getNamingRule(id).body()
        Log.i(tag, "Rule found : $namingRuleInfo")
        return namingRuleInfo?.let { NamingRuleModel(it) }
    }

    suspend fun editNamingRule(id: String, replace: String, replaceBy: String, priority: Int) {
        namingRulesAPI.postNamingRule(id, NamingRuleInfo(id, replace, replaceBy, priority))
    }

    suspend fun deleteNamingRule(id: String) {
        namingRulesAPI.deleteNamingRule(id)
    }
}