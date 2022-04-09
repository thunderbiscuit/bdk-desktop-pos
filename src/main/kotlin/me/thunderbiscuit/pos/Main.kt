package me.thunderbiscuit.pos

import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App(PoSWallet: Wallet) {
    var address by remember { mutableStateOf("No address yet") }

    MaterialTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                bitmap = if (address == "No address yet") buildQRCode("https://bitcoindevkit.org/") else buildQRCode(address),
                contentDescription = "Address QR Code",
                modifier = Modifier.size(250.dp)
            )

            Spacer(Modifier.padding(12.dp))

            Text(text = address)

            Spacer(Modifier.padding(24.dp))

            Button(onClick = {
                address = PoSWallet.getNewAddress()
            }) {
                Text(text = "Generate a New Address")
            }
        }
    }
}

fun main() {
    val PoSWallet: Wallet = Wallet

    application {
        Window(onCloseRequest = ::exitApplication) {
            App(PoSWallet)
        }
    }
}
