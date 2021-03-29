package com.jgdeveloppement.pizza_serradifalco.details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

import com.jgdeveloppement.pizza_serradifalco.R
import com.jgdeveloppement.pizza_serradifalco.databinding.FragmentDetailsBinding
import com.jgdeveloppement.pizza_serradifalco.home.HomeActivity
import com.jgdeveloppement.pizza_serradifalco.injection.Injection
import com.jgdeveloppement.pizza_serradifalco.models.Product
import com.jgdeveloppement.pizza_serradifalco.models.ShoppingRow
import com.jgdeveloppement.pizza_serradifalco.utils.Status
import com.jgdeveloppement.pizza_serradifalco.utils.UserData
import com.jgdeveloppement.pizza_serradifalco.utils.Utils
import com.jgdeveloppement.pizza_serradifalco.viewmodel.ProductViewModel
import com.skydoves.doublelift.DoubleLiftLayout


class DetailsFragment : Fragment(), DetailsProductAdapter.OnSelectOtherProductListener {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var productViewModel: ProductViewModel
    private var navController: NavController? = null
    private var productId: Int? = null
    private var quantityItem: Int = 1
    private var shoppingRow: ShoppingRow = ShoppingRow()
    private var shoppingRowList: MutableList<ShoppingRow> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setupViewModel()
        setHasOptionsMenu(true)
        if (arguments != null){
            productId = requireArguments().getInt(Utils.PRODUCT_ID)
            initProduct(productId!!)
        }

        getProductList(Utils.DRINK, binding.drinkRecyclerView, R.layout.add_product_item)
        getProductList(Utils.DESSERT, binding.dessertRecyclerView, R.layout.add_product_item)
        getProductList(Utils.EXTRA, binding.extraRecyclerView, R.layout.add_extra_item)

        setCollapseExpandView()
        radioGroupPriceChangeListener()
        spinnerQuantityChangeListener()
        addButtonClickListener()

    }

    private fun setupViewModel() {
        productViewModel = ViewModelProviders.of(this, Injection.provideProductViewModelFactory()).get(ProductViewModel::class.java)
    }

    //Listener
    private fun radioGroupPriceChangeListener(){
        binding.radioGroupPrice.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = group.findViewById(checkedId)
            if (radio.tag == Utils.LARGE) getProductList(Utils.EXTRA, binding.extraRecyclerView, R.layout.add_extra_item, true)
            else getProductList(Utils.EXTRA, binding.extraRecyclerView, R.layout.add_extra_item, false)
            initProduct(productId!!, quantityItem)
        }
    }

    private fun spinnerQuantityChangeListener(){
        binding.detailsItemQuantitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val item : String = p0?.adapter?.getItem(p2).toString()
                quantityItem = item.toInt()
                initProduct(productId!!, item.toInt())
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }
    }

    private fun addButtonClickListener(){
        binding.detailsAddButton.setOnClickListener {
            shoppingRowList.add(shoppingRow)
            UserData.shoppingRowList.addAll(shoppingRowList)
            requireActivity().invalidateOptionsMenu()
            navController!!.navigateUp()
        }
    }

    // Init view with details Item
    private fun initProduct(id: Int, quantity: Int = 1){
        productViewModel.getPizzaById(id).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.detailsProgressLayout.visibility = View.GONE
                        resource.data?.let { product -> initViewWithDetailsItem(product, quantity); setShoppingRow(product, quantity, shoppingRow)}
                    }
                    Status.ERROR -> {
                        binding.detailsProgressLayout.visibility = View.GONE
                        Utils.showSnackBar(binding.detailsLayout, getString(R.string.error_occurred))
                    }
                    Status.LOADING -> {
                        binding.detailsProgressLayout.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun initViewWithDetailsItem(product: Product, quantity: Int){
        val mediumPrice = product.mediumPrice * quantity
        val largePrice = product.largePrice * quantity
        binding.detailsCardPizzaName.text = product.name.replace("_", " ")
        binding.detailsCardPizzaConponent.text = product.component
        binding.radioButtonMiddleSize.text = getString(R.string.medium_price, String.format("%.1f", mediumPrice))
        binding.radioButtonLargeSize.text = getString(R.string.large_price, String.format("%.1f", largePrice))
        if (product.largePrice == 0.0){
            binding.radioButtonLargeSize.isEnabled = false
            binding.radioButtonLargeSize.text = getString(R.string.no_price)
        }
        if (product.category == Utils.DESSERT_CATEGORY){
            binding.detailsDessertDoubleLift.visibility = View.GONE
            binding.detailsSupplementDoubleLift.visibility = View.GONE
        }
    }

    private fun setShoppingRow(product: Product, quantity: Int, shoppingRow: ShoppingRow){
        val size = if (binding.radioButtonLargeSize.isChecked) Utils.LARGE else Utils.MIDDLE
        val price = if(size == Utils.LARGE) product.largePrice  else product.mediumPrice

        shoppingRow.productName = product.name.replace("_", " ")
        shoppingRow.size = size
        shoppingRow.quantity = quantity
        shoppingRow.price = price
        shoppingRow.extra = Utils.NONE_FR

    }

    //Collapse expand method
    private fun setCollapseExpandView(){
        binding.detailsDessertTitle.setOnClickListener { setView(binding.detailsDessertTitle, binding.detailsDessertDoubleLift) }
        binding.detailsDrinkTitle.setOnClickListener { setView(binding.detailsDrinkTitle, binding.detailsDrinkDoubleLift) }
        binding.detailsSupplementTitle.setOnClickListener { setView(binding.detailsSupplementTitle, binding.detailsSupplementDoubleLift) }
    }

    private fun setView(textView: TextView, cardView: DoubleLiftLayout){
        if (cardView.isExpanded){
            cardView.collapse()
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0)
        } else {
            cardView.expand()
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0)
        }
    }

    //Method for RecyclerView
    private fun getProductList(type: String, recyclerView: RecyclerView, layoutId: Int, isLarge: Boolean = false){
        productViewModel.getProductList(type).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.detailsProgressLayout.visibility = View.GONE
                        resource.data?.let { productList ->
                            recyclerView.adapter = DetailsProductAdapter(context as HomeActivity, productList, layoutId, isLarge, this)
                        }
                    }
                    Status.ERROR -> {
                        binding.detailsProgressLayout.visibility = View.GONE
                        Utils.showSnackBar(binding.detailsLayout, getString(R.string.error_occurred))
                    }
                    Status.LOADING -> {
                        binding.detailsProgressLayout.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    override fun onSpinnerChangeListener(product: Product, quantity: Int) {
        val shoppingRowFromSpinner = ShoppingRow()
        shoppingRowFromSpinner.productName = product.name
        shoppingRowFromSpinner.quantity = quantity
        shoppingRowFromSpinner.price = product.mediumPrice
        shoppingRowList.add(shoppingRowFromSpinner)
        shoppingRowList.removeIf { it.quantity == 0 }
        shoppingRowList.removeIf { quantity == 0 && it.productName == product.name }

    }

    override fun onCheckBoxSelectListener(product: Product, isLarge: Boolean) {
        val largePrice = product.largePrice
        val mediumPrice = product.mediumPrice
        if (shoppingRow.extra == Utils.NONE_FR) shoppingRow.extra = product.name else shoppingRow.extra += ", ${product.name}"
        if (isLarge) shoppingRow.price += largePrice else shoppingRow.price += mediumPrice
    }

}
