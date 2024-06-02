package org.repoClone

import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.system.exitProcess

fun request(usr: String): String{
  val client = OkHttpClient()
  val req = Request.Builder().url("https://api.github.com/users/${usr}/repos?per_page=200").build()
  var storeRequest: String
  client.newCall(req).execute().use { response ->
    storeRequest = response.body!!.string()
  }
  return storeRequest
}


fun main() {
  print("Enter user name: ")
  val userName = readln()
  val result = request(userName)
  for (i in result.split(",")) {
    if (i.contains("clone_url")){
      val temp = i.split("https")[1].replace("\"","")
      val compileIt = listOf("git", "clone", "https${temp}")
      ProcessBuilder(compileIt).redirectError(ProcessBuilder.Redirect.INHERIT).start().waitFor()
    }
  }
}