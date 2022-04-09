package me.thunderbiscuit.pos

import org.bitcoindevkit.*
import java.nio.file.Files
import org.bitcoindevkit.Wallet as BdkWallet

private const val TAG = "Wallet"

object Wallet {

    private val path: String = Files.createTempDirectory("desktop-PoS").toString()
    private const val electrumURL: String = "ssl://electrum.blockstream.info:60002"
    private const val descriptor = "wpkh([c258d2e4/84h/1h/0h]tpubDDYkZojQFQjht8Tm4jsS3iuEmKjTiEGjG6KnuFNKKJb5A6ZUCUZKdvLdSDWofKi4ToRCwb9poe1XdqfUnP4jaJjCB2Zwv11ZLgSbnZSNecE/0/*)"
    private val database = DatabaseConfig.Sqlite(SqliteDbConfiguration("$path/bdk-sqlite"))
    private val blockchain = BlockchainConfig.Electrum(ElectrumConfig(electrumURL, null, 5u, null, 10u))

    private val wallet: BdkWallet = BdkWallet(
        descriptor = descriptor,
        changeDescriptor = null,
        Network.TESTNET,
        databaseConfig = database,
        blockchainConfig = blockchain
    )

    object LogProgress: BdkProgress {
        override fun update(progress: Float, message: String?) {
            println("updating wallet $progress $message")
            // Log.d(TAG, "updating wallet $progress $message")
        }
    }

    fun sync(): Unit {
        wallet.sync(LogProgress, null)
    }

    fun getNewAddress(): String {
        return wallet.getNewAddress()
    }
}
