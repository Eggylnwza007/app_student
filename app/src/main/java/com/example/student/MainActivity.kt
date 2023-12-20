package com.example.student

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.student.ui.theme.StudentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentTheme {
                val viewModel = remember { StudentViewModel() } // สร้าง ViewModel ด้วย remember
                StudentApp(viewModel)
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentApp(viewModel: StudentViewModel){ // รับ ViewModel เข้ามาเพื่อใช้งาน
    var viewModel = StudentViewModel()
    var isShowDialog = remember {
        mutableStateOf(false)
    }
    if(isShowDialog.value){
        InputDialog(
            onAddButton = { name, studentId ->
                viewModel.addStudent(name, studentId) // เรียกใช้งาน addStudent ใน ViewModel เมื่อมีการเพิ่มข้อมูล
                isShowDialog.value = false
            },
            onCancle = {
                isShowDialog.value = false
            }
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Student App")},
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Magenta,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isShowDialog.value = true },
                containerColor = Color.Magenta,
                contentColor = Color.White) {
                Icon(Icons.Filled.Add,
                    "Add new student")
            }
        }
    ){
        LazyColumn(
            modifier = Modifier.padding(it)){
            items(viewModel.data){
                    item->Text(item.name)
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDialog(
    onCancle: () -> Unit,
    onAddButton: (String, String) -> Unit // รับพารามิเตอร์สองตัวเพื่อรับชื่อและรหัสนักศึกษา
){
    Dialog(onDismissRequest = onCancle) {
        var inputName by remember {
            mutableStateOf("")
        }
        var inputId by remember {
            mutableStateOf("")
        }
        Card(modifier = Modifier.padding(10.dp)){
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                OutlinedTextField(
                    value = inputId,
                    onValueChange = { inputId = it },
                    label = { Text("Student id")})
                OutlinedTextField(
                    value = inputName,
                    onValueChange = { inputName = it },
                    label = { Text("Student name")})
                TextButton(
                    onClick = {
                        onAddButton(inputName, inputId) // เรียกใช้งาน onAddButton และส่งข้อมูลชื่อและรหัสนักศึกษาไปยังฟังก์ชันที่เรียก
                    },
                    colors =  ButtonDefaults.buttonColors(
                        containerColor = Color.Magenta),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Text("Add")
                }
            }}
    }
}
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    StudentTheme {
        InputDialog(
            onCancle = {},
            onAddButton = { name, id ->
                // Do something with the provided name and id
            }
        )
    }
}