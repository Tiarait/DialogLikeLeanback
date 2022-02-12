# LeanbackDialogFragment
LeanbackDialogFragment extend from DialogFragment and be customized like leanbeak dialog permission

## Getting Started
* Copy code [LeanbackDialogFragment.kt](https://github.com/Tiarait/DialogLikeLeanback/blob/master/app/src/main/java/example/leanback/dialog/LeanbackDialogFragment.kt)
* Copy xml [fragment_leanback_dialog.xml](https://github.com/Tiarait/DialogLikeLeanback/blob/master/app/src/main/res/layout/fragment_leanback_dialog.xml)
* Copy styles [res/styles](https://github.com/Tiarait/DialogLikeLeanback/blob/master/app/src/main/res/values/styles.xml), You also can add your custom style when created dialog
* Copy dimens [res/dimens](https://github.com/Tiarait/DialogLikeLeanback/blob/master/app/src/main/res/values/dimens.xml)
* See last 3 colors in [res/colors](https://github.com/Tiarait/DialogLikeLeanback/blob/master/app/src/main/res/values/colors.xml), You also can change bg color in style
* Create dialog
```
  //See constructors in LeanbackDialogFragment
  val dialogExit = LeanbackDialogFragment(R.string.dialog_exit, R.string.dialog_exit_d, R.drawable.ic_exit)
  //You can replace style here or in style.xml
  //dialogExit.styleRes = R.style.TransparentDialog

  //Add button to dialog (max 3)
  dialogExit.addButton(R.string.yes) { dialog, which ->
      //Its DialogInterface.OnClickListener
      exitProcess(0)
  }
  dialogExit.addButton(R.string.no)
  dialogExit.addButton("I don't know")
  dialogExit.show(requireActivity().supportFragmentManager)
```

#### Attention! I created in xml 3 a textview for the buttons, although the best solution would be to create and configure a Recycler there to add many elements (but this result suits me too)

---

### Screenshot origin leanback dialog, and my LeanbackDialogFragment:
<p align="center">
  <img src="https://github.com/Tiarait/DialogLikeLeanback/blob/master/Screenshot_orig.png" width="500" title="origin leanback dialog">
  <img src="https://github.com/Tiarait/DialogLikeLeanback/blob/master/Screenshot_custom.png" width="500" title="LeanbackDialogFragment">
</p>


## License 

    Copyright 2022

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
