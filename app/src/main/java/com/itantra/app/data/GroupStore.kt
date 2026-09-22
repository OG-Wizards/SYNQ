package com.itantra.app.data

import android.content.Context
import com.itantra.app.model.Group
import org.json.JSONArray
import org.json.JSONObject

class GroupStore(context: Context) {
    private val prefs = context.getSharedPreferences("groups", Context.MODE_PRIVATE)
    fun all(): List<Group> = runCatching {
        val a = JSONArray(prefs.getString("data", "[]"))
        (0 until a.length()).map { i -> val o=a.getJSONObject(i); Group(o.getLong("id"), o.getString("name"), (0 until o.getJSONArray("members").length()).map { j -> o.getJSONArray("members").getString(j) }) }
    }.getOrDefault(emptyList())
    fun add(name: String, members: List<String>): List<Group> { val list=all().toMutableList(); list += Group(System.currentTimeMillis(), name, members); save(list); return list }
    fun delete(id: Long): List<Group> { val list=all().filterNot { it.id==id }; save(list); return list }
    private fun save(list: List<Group>) { val a=JSONArray(); list.forEach { g -> a.put(JSONObject().apply { put("id",g.id); put("name",g.name); put("members",JSONArray(g.members)) }) }; prefs.edit().putString("data",a.toString()).apply() }
}
