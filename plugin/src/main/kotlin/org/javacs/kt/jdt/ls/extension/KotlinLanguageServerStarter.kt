package org.javacs.kt.jdt.ls.extension

import java.util.concurrent.Executors
import org.eclipse.lsp4j.launch.LSPLauncher
import org.javacs.kt.KotlinLanguageServer
import org.javacs.kt.util.ExitingInputStream
import org.javacs.kt.util.tcpStartServer
import org.javacs.kt.util.tcpConnectToClient

import org.eclipse.jdt.ls.core.internal.IDelegateCommandHandler
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Platform
import org.eclipse.core.runtime.FileLocator


class KotlinLanguageServerStarter: IDelegateCommandHandler {
    override fun executeCommand(commandId: String, arguments: List<Any>?, progress: IProgressMonitor) {
        KotlinLogger.logInfo("KotlinLanguageServerStarter executeCommand: ${commandId}, ${arguments}");
        arguments?.firstOrNull()?.let {
            if (it is Number) {
                val port = it.toInt();
                this.start(port, false)
            }
        }
    }

    fun start(port: Int, listen: Boolean) {
        KotlinLogger.logInfo("KotlinLanguageServerStarter start: port = ${port}, listen = ${listen}");
        val (inStream, outStream) = if (listen) {
            // Launch as TCP Server
            tcpStartServer(port)
        } else {
            // Launch as TCP Client
            tcpConnectToClient("localhost", port)
        }

        val compilerBundle = KotlinPlugin.getDefault().getBundle()
        val compilerPath = FileLocator.toFileURL(compilerBundle.getEntry("/")).file + "libs/kotlin-compiler-1.6.10.jar"

        val server = KotlinLanguageServer(compilerPath)
        val threads = Executors.newSingleThreadExecutor { Thread(it, "client") }
        val launcher = LSPLauncher.createServerLauncher(server, ExitingInputStream(inStream), outStream, threads) { it }

        server.connect(launcher.remoteProxy)
        launcher.startListening()
    }
}
