package com.homan.huang.netgeardemo04142021.hellper

import android.content.Context
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT

fun msg(context: Context, s: String, len: Int) =
    if (len > 0) Toast.makeText(context, s, LENGTH_LONG).show()
    else Toast.makeText(context, s, LENGTH_SHORT).show()