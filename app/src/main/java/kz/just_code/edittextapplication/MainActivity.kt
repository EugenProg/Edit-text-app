package kz.just_code.edittextapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import kz.just_code.edittextapplication.databinding.ActivityMainBinding
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.slots.Slot
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import java.util.Locale

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