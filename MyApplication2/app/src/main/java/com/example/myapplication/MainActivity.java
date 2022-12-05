package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button Left = findViewById(R.id.Left_botton);
        Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = "1";
                WriteText();
                writeToFile("file.txt", content);
            }
        });
        Button Right = findViewById(R.id.Right_botton);
        Button Drop = findViewById(R.id.drop_botton);
        Button Rotate = findViewById(R.id.drop_botton);
    }
    public void  writeToFile(String filename, String content){
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, filename));
            writer.write(content.getBytes());
            writer.close();
            //Toast.makeText(getApplicationContext(), "wrote to file ", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void WriteText() {
        Button txt= (Button) findViewById(R.id.Left_botton);


        try {
            BufferedWriter fos = new BufferedWriter(new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+"File.txt"));
            fos.write(txt.getText().toString().trim());
            fos.close();
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG);
        }
    }
}