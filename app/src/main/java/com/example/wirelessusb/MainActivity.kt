package com.example.wirelessusb

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.wirelessusb.ui.theme.WirelessUSBTheme

class MainActivity : ComponentActivity() {

    private lateinit var ftpServerManager: FtpServerManager
    private var hasStoragePermission = mutableStateOf(false)

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasStoragePermission.value = permissions.values.all { it }
    }

    private val manageStorageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            hasStoragePermission.value = Environment.isExternalStorageManager()
        }
    }

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ftpServerManager = FtpServerManager(this)
        checkAndRequestPermissions()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        setContent {
            WirelessUSBTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFC0C0C0)
                ) {
                    FileServerScreen()
                }
            }
        }
    }

    private fun checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            hasStoragePermission.value = Environment.isExternalStorageManager()
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.parse("package:$packageName")
                manageStorageLauncher.launch(intent)
            }
        } else {
            val readGranted = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
            if (!readGranted) {
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                )
            } else {
                hasStoragePermission.value = true
            }
        }
    }

    private fun startForegroundService(ip: String, port: Int) {
        val intent = Intent(this, FtpForegroundService::class.java).apply {
            putExtra(FtpForegroundService.EXTRA_IP, ip)
            putExtra(FtpForegroundService.EXTRA_PORT, port)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    private fun stopForegroundService() {
        stopService(Intent(this, FtpForegroundService::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        ftpServerManager.stop()
        stopForegroundService()
    }

    @Composable
    fun FileServerScreen() {
        var isRunning by remember { mutableStateOf(false) }
        var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }
        var username by remember { mutableStateOf("android") }
        var password by remember { mutableStateOf("android") }
        var showPassword by remember { mutableStateOf(false) }
        val permissionGranted by hasStoragePermission

        val ip = ftpServerManager.getIpAddress()
        val port = ftpServerManager.port
        val ftpUrl = "ftp://$ip:$port"

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFC0C0C0))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 128.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // ── Main App Window ──
            Win98Window(
                title = "WirelessUSB — FTP File Transfer",
                modifier = Modifier.fillMaxWidth()
            ) {

                // Permission Warning
                if (!permissionGranted) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFC0C0C0))
                            .win98Sunken()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "⚠ Storage permission required!",
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF800000)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Win98Button(
                            text = "Grant Permission",
                            onClick = { checkAndRequestPermissions() },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Status Bar
                Win98StatusBar(isRunning = isRunning)
                Spacer(modifier = Modifier.height(8.dp))

                // Credentials section (only when stopped)
                if (!isRunning) {
                    // Groupbox style
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .win98Sunken()
                            .padding(8.dp)
                    ) {
                        Column {
                            Text(
                                text = "Login Credentials",
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Win98TextField(
                                value = username,
                                onValueChange = { username = it },
                                label = "Username:",
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Win98TextField(
                                value = password,
                                onValueChange = { password = it },
                                label = "Password:",
                                modifier = Modifier.fillMaxWidth(),
                                visualTransformation = if (showPassword)
                                    VisualTransformation.None
                                else
                                    PasswordVisualTransformation()
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            // Checkbox row
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(start = 2.dp)
                            ) {
                                // Win98 style checkbox
                                Box(
                                    modifier = Modifier
                                        .size(14.dp)
                                        .background(Color.White)
                                        .win98Sunken()
                                        .pointerInput(Unit) {
                                            detectTapGestures {
                                                showPassword = !showPassword
                                            }
                                        }
                                    ,
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (showPassword) {
                                        Text(
                                            text = "✓",
                                            fontSize = 10.sp,
                                            fontFamily = FontFamily.Monospace,
                                            color = Color.Black
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Show Password",
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Server Info (when running)
                if (isRunning) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .win98Sunken()
                            .padding(8.dp)
                    ) {
                        Column {
                            Text(
                                text = "Server Information",
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            // URL row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Address:",
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.Black,
                                    modifier = Modifier.width(70.dp)
                                )
                                Text(
                                    text = ftpUrl,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF000080),
                                    modifier = Modifier
                                        .background(Color.White)
                                        .win98Sunken()
                                        .padding(4.dp)
                                        .fillMaxWidth()
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "User:",
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.Black,
                                    modifier = Modifier.width(70.dp)
                                )
                                Text(
                                    text = username,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .background(Color.White)
                                        .win98Sunken()
                                        .padding(4.dp)
                                        .fillMaxWidth()
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Pass:",
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.Black,
                                    modifier = Modifier.width(70.dp)
                                )
                                Text(
                                    text = password,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .background(Color.White)
                                        .win98Sunken()
                                        .padding(4.dp)
                                        .fillMaxWidth()
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "ℹ Same WiFi required on PC",
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                color = Color(0xFF404040)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // QR Code window
                    qrBitmap?.let { bitmap ->
                        Win98Window(
                            title = "QR Code — Scan to Connect",
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(200.dp)
                                        .background(Color.White)
                                        .win98Sunken()
                                        .padding(8.dp)
                                ) {
                                    Image(
                                        bitmap = bitmap.asImageBitmap(),
                                        contentDescription = "QR Code",
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Action Buttons row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Win98Button(
                        text = if (isRunning) "Stop Server" else "Start Server",
                        isRed = isRunning,
                        onClick = {
                            if (isRunning) {
                                ftpServerManager.stop()
                                stopForegroundService()
                                qrBitmap = null
                                isRunning = false
                            } else {
                                val rootPath =
                                    Environment.getExternalStorageDirectory().absolutePath
                                val success =
                                    ftpServerManager.start(rootPath, username, password)
                                if (success) {
                                    qrBitmap = QrCodeGenerator.generateQrCode(ftpUrl)
                                    startForegroundService(ip, port)
                                    isRunning = true
                                }
                            }
                        },
                        modifier = Modifier.width(140.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Bottom status bar (Win98 style taskbar-like)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .win98Raised()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isRunning) "● Active on $ip:$port"
                    else "● Ready",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
