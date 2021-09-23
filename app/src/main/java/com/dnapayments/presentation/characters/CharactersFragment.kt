package com.dnapayments.presentation.characters

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dnapayments.R
import com.dnapayments.databinding.FragmentCharactersBinding
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CharactersFragment :
    BaseBindingFragment<FragmentCharactersBinding>(R.layout.fragment_characters) {
    private var characterAdapter: CharacterAdapter? = null
    private val vm: CharacterViewModel by sharedViewModel()
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.apply {
            showLoader()
            viewModel = vm
            characterAdapter = CharacterAdapter {
                findNavController().navigate(R.id.action_characters_to_details,
                    Bundle().apply {
                        putInt("char_id", it)
                    })
            }
            recyclerView.adapter = characterAdapter
            vm.characterList.observe(viewLifecycleOwner, {
                characterAdapter?.setData(it)
                hideLoader()
            })
        }
    }
}