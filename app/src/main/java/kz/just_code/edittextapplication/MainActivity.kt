package kz.just_code.edittextapplication

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import kz.just_code.edittextapplication.databinding.ActivityMainBinding
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.slots.Slot
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val dateMask = MaskImpl.createTerminated(DATE_MASK)
    private val phoneMask = MaskImpl.createTerminated(MY_PHONE_FORMAT)
    private val dateWatcher = MaskFormatWatcher(dateMask)
    private val phoneWatcher = MaskFormatWatcher(phoneMask)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpMask()

        binding.showPhoneButton.setOnClickListener {
            /*var number = ""
            binding.editText.text.forEach {
                if (it.isDigit()) number += it
            }

            Toast.makeText(this, number, Toast.LENGTH_SHORT).show()*/

            /*val phoneNumber = PhoneNumberUtils.formatNumber("+497778889900", "DEU")
            Toast.makeText(this, phoneNumber, Toast.LENGTH_LONG).show()*/

            binding.editText.hideKeyboard()
        }

        phoneWatcher.installOn(binding.inputView)

        KeyboardVisibilityEvent.setEventListener(this) { isOpen ->
            binding.showPhoneButton.isVisible = !isOpen
        }

        binding.editText.addTextChangedListener()
        binding.cityView.movementMethod = LinkMovementMethod.getInstance()
        binding.cityView.text = getCity()
    }

    private fun getCity(): SpannableString {
        val cityName = "Netherlands, Amesterdam"
        val spannable = SpannableString("$cityName ${getString(R.string.change_location)}")

        val color = Color.parseColor("#8E63EE")
        spannable.setSpan(ForegroundColorSpan(color), cityName.length + 1, spannable.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        spannable.setSpan(UnderlineSpan(), cityName.length + 1, spannable.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            cityName.length + 1,
            spannable.length,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            StyleSpan(Typeface.ITALIC),
            0,
            cityName.length,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@MainActivity, "Click at location", Toast.LENGTH_SHORT).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true
                ds.linkColor = Color.parseColor("#8E63EE")
            }
        }

        spannable.setSpan(
            clickableSpan,
            cityName.length + 1,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    override fun onResume() {
        super.onResume()
        binding.editText.showKeyboard()
    }

    private fun setUpMask() {
        dateWatcher.installOn(binding.editText)
    }
}

val DATE_MASK: Array<Slot> = arrayOf(
    PredefinedSlots.digit(),
    PredefinedSlots.digit(),
    PredefinedSlots.hardcodedSlot('.'),
    PredefinedSlots.digit(),
    PredefinedSlots.digit(),
    PredefinedSlots.hardcodedSlot('.'),
    PredefinedSlots.digit(),
    PredefinedSlots.digit(),
    PredefinedSlots.digit(),
    PredefinedSlots.digit(),
)

val MY_PHONE_FORMAT: Array<Slot> = arrayOf(
    PredefinedSlots.digit(),
    PredefinedSlots.digit(),
    PredefinedSlots.digit(),
    PredefinedSlots.hardcodedSlot(')'),
    PredefinedSlots.hardcodedSlot(' '),
    PredefinedSlots.digit(),
    PredefinedSlots.digit(),
    PredefinedSlots.digit(),
    PredefinedSlots.hardcodedSlot('-'),
    PredefinedSlots.digit(),
    PredefinedSlots.digit(),
    PredefinedSlots.hardcodedSlot('-'),
    PredefinedSlots.digit(),
    PredefinedSlots.digit()
)