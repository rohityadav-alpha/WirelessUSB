package com.example.wirelessusb

import android.content.Context
import org.apache.ftpserver.FtpServerFactory
import org.apache.ftpserver.ftplet.Authority
import org.apache.ftpserver.listener.ListenerFactory
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory
import org.apache.ftpserver.usermanager.impl.BaseUser
import org.apache.ftpserver.usermanager.impl.WritePermission
import java.net.Inet4Address
import java.net.NetworkInterface

class FtpServerManager(private val context: Context) {

    private var ftpServer: org.apache.ftpserver.FtpServer? = null
    val port = 2221

    fun start(rootPath: String, username: String, password: String): Boolean {
        return try {
            val serverFactory = FtpServerFactory()

            val listenerFactory = ListenerFactory()
            listenerFactory.port = port
            serverFactory.addListener("default", listenerFactory.createListener())

            val userManagerFactory = PropertiesUserManagerFactory()
            val userManager = userManagerFactory.createUserManager()

            val user = BaseUser()
            user.name = username
            user.password = password
            user.homeDirectory = rootPath
            user.authorities = listOf<Authority>(WritePermission())
            user.maxIdleTime = 0

            userManager.save(user)
            serverFactory.userManager = userManager

            val connectionConfig = org.apache.ftpserver.ConnectionConfigFactory()
            connectionConfig.maxThreads = 10
            serverFactory.connectionConfig = connectionConfig.createConnectionConfig()

            ftpServer = serverFactory.createServer()
            ftpServer?.start()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun stop() {
        ftpServer?.stop()
        ftpServer = null
    }

    fun isRunning(): Boolean {
        return ftpServer != null && !ftpServer!!.isStopped
    }

    fun getIpAddress(): String {
        return try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            for (networkInterface in interfaces) {
                if (networkInterface.isLoopback || !networkInterface.isUp) continue
                for (address in networkInterface.inetAddresses) {
                    if (address.isLoopbackAddress) continue
                    if (address is Inet4Address) {
                        return address.hostAddress ?: continue
                    }
                }
            }
            "Not connected"
        } catch (e: Exception) {
            "Not connected"
        }
    }
}
