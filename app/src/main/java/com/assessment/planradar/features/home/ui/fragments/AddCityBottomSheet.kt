package com.assessment.planradar.features.home.ui.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import com.assessment.planradar.R
import com.assessment.planradar.R.string
import com.assessment.planradar.databinding.SheetAddCityBinding
import com.assessment.planradar.features.home.ui.viewmodels.MainViewModel
import com.assessment.planradar.utils.appComponent
import com.assessment.planradar.utils.result.Resource
import com.assessment.planradar.utils.result.ResourceType
import com.assessment.planradar.utils.showLoading
import com.assessment.planradar.utils.viewmodel.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class AddCityBottomSheet : BottomSheetDialogFragment() {
  @Inject lateinit var viewModelFactory: ViewModelFactory
  private val mainViewModel: MainViewModel by activityViewModels { viewModelFactory }

  private lateinit var binding: SheetAddCityBinding

  companion object {
    @JvmStatic
    fun show(navController: NavController) =
      navController.navigate(R.id.action_navigate_to_addCityFragment)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val rootView: View = inflater.inflate(R.layout.sheet_add_city, container, false)
    binding = SheetAddCityBinding.bind(rootView)
    appComponent.inject(this)
    return rootView
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    setupAddCityEditText()
  }

  private fun setupAddCityEditText() {
    binding.etAddNewCity.setOnKeyListener { _, keyCode, event ->
      if (keyCode == KeyEvent.KEYCODE_ENTER && event?.action == KeyEvent.ACTION_DOWN) {
        checkAddCity()
      }
      true
    }
  }

  private fun checkAddCity() {
    mainViewModel.checkAddCity(binding.etAddNewCity.text.toString()).observe(
      viewLifecycleOwner,
      this::handleAddingCityResult
    )
  }

  private fun handleAddingCityResult(result: Resource<Any>) {
    when (result.resourceType) {
      ResourceType.LOADING -> setLoading()
      ResourceType.SUCCESS -> {
        dismiss()
        Toast.makeText(context, getString(string.city_added), Toast.LENGTH_SHORT).show()
      }
      else -> {
        dismiss()
        Toast.makeText(context, getString(string.city_not_found), Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun setLoading() = binding.layoutProgress.showLoading(true)

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle)
    val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
    bottomSheetDialog.setOnShowListener { dialogInterface: DialogInterface ->
      val dialog = dialogInterface as BottomSheetDialog
      val bottomSheet =
        dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
      if (bottomSheet != null) {
        BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
        BottomSheetBehavior.from(bottomSheet).skipCollapsed = true
        BottomSheetBehavior.from(bottomSheet).isHideable = true
      }
    }
    return bottomSheetDialog
  }

}