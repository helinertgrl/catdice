package com.example.catdice

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rollDice()

        val rollbutton: Button = findViewById(R.id.rollbutton)
        val bigPaw = findViewById<ImageView>(R.id.bigpaw)

        rollbutton.setOnClickListener {

            val diceImages = arrayOf(
                R.drawable.dice1,
                R.drawable.dice2,
                R.drawable.dice3,
                R.drawable.dice4,
                R.drawable.dice5,
                R.drawable.dice6
            )
            val diceimage: ImageView = findViewById(R.id.imageView)
            val diceimage2: ImageView = findViewById(R.id.imageView2)

            val handler = android.os.Handler(mainLooper)
            var count = 0
            val runnable = object : Runnable {
                override fun run() {
                    diceimage.setImageResource(diceImages[count % 6])
                    diceimage2.setImageResource(diceImages[(count + 3) % 6]) // biraz farklı hareket için +3
                    count++
                    if (count < 12) {
                        handler.postDelayed(this, 50)
                    } else {
                        rollDice()  // animasyon bitince gerçek zarları göster
                    }
                }
            }
            handler.post(runnable)

            // bigPaw animasyonun zaten var, aynen kalsın:
            val bigPaw = findViewById<ImageView>(R.id.bigpaw)
            bigPaw.animate()
                .translationYBy(-750f)
                .setDuration(300)
                .withEndAction {
                    bigPaw.animate()
                        .translationYBy(750f)
                        .setDuration(300)
                        .start()
                }
                .start()


            //val toastMA = Toast.makeText(this, "Dice Rolled!", Toast.LENGTH_SHORT)
            //toastMA.show()
        }
    }

    private fun rollDice() {
        val diceMA = Dice(6)
        val diceMA2 = Dice(6)
        val cubeRoll = diceMA.rolldice()
        val cubeRoll2 = diceMA.rolldice()
       // val fakezarımız: TextView = findViewById(R.id.fakezar)
       //fakezarımız.text = cubeRoll.toString()
        val diceimage: ImageView = findViewById(R.id.imageView)
        val diceimage2: ImageView = findViewById(R.id.imageView2)

        val bigcat: ImageView = findViewById(R.id.bigcat)
        bigcat.setImageResource(R.drawable.bigcat)
        val littlecat: ImageView = findViewById(R.id.littlecat)
        littlecat.setImageResource(R.drawable.littlecat)
        val rollpaw: ImageView = findViewById(R.id.rollpaw)
        rollpaw.setImageResource(R.drawable.rollpaw)
        val paw: ImageView = findViewById(R.id.bigpaw)
        paw.setImageResource(R.drawable.paw)

        when (cubeRoll) {
            1 -> diceimage.setImageResource(R.drawable.dice1)
            2 -> diceimage.setImageResource(R.drawable.dice2)
            3 -> diceimage.setImageResource(R.drawable.dice3)
            4 -> diceimage.setImageResource(R.drawable.dice4)
            5 -> diceimage.setImageResource(R.drawable.dice5)
            6 -> diceimage.setImageResource(R.drawable.dice6)
        }

        when (cubeRoll2) {
            1 -> diceimage2.setImageResource(R.drawable.dice1)
            2 -> diceimage2.setImageResource(R.drawable.dice2)
            3 -> diceimage2.setImageResource(R.drawable.dice3)
            4 -> diceimage2.setImageResource(R.drawable.dice4)
            5 -> diceimage2.setImageResource(R.drawable.dice5)
            6 -> diceimage2.setImageResource(R.drawable.dice6)
        }
    }


    class Dice(val numSidesMA: Int) {
        fun rolldice(): Int {
            return (1..numSidesMA).random()
        }
    }
}