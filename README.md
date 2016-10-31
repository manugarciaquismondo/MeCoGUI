# MeCoGUI
A minimalistic interface for pLinguaCore

MeCoGUI is a minimalistic Graphic User Interface (GUI) for the modeling and simulation of P-Lingua models using pLinguaCore. MeCoGUI provides controls to parse, simulate and export P system specifications.

To run MeCoGUI, first compile the source files from pLinguaCore and MeCoGUI into binary (.class) Java files. Upon running MeCoGUI, the following interface must appear:

![MeCoGUI main screen](https://github.com/manugarciaquismondo/MeCoGUI/blob/master/images/mecogui_main.png)

To load a P-Lingua model, click on _File_>_Load Model_ and select the P-Lingua specification to simulate.

![Load a P-Lingua model](https://github.com/manugarciaquismondo/MeCoGUI/blob/master/images/mecogui_load_model.png)

Then, click on _File_>_Load Data_ and select the parameters of the model in _.CSV_ format. 

![Load model parameters](https://github.com/manugarciaquismondo/MeCoGUI/blob/master/images/mecogui_load_data.png)

The parameters file must contain a _steps_per_cycle_ parameter indicating how many steps consist a cycle on.

![Steps per cycle](https://github.com/manugarciaquismondo/MeCoGUI/blob/master/images/steps_per_cycle.png)

Optionally, you can also set a report file with simulation statistics clicking on _File_>_Set Report_.

![Set a report file](https://github.com/manugarciaquismondo/MeCoGUI/blob/master/images/mecogui_set_report.png)

The routes of the selected files will appear in MeCoGUI.

![MeCoGUI route display](https://github.com/manugarciaquismondo/MeCoGUI/blob/master/images/mecogui_routes_set.png)

Once the _Model_ and _Data_ file routes have been set, the P system can be parsed clicking on _Initialize_.

![Initialize model](https://github.com/manugarciaquismondo/MeCoGUI/blob/master/images/mecogui_initialize.png)

If the parsing process generated no errors, the parsed model should appear on the _Info console_ tab. Otherwise, errors will appear on the _Error console_ tab. Likewise, any warnings will appear on the _Warning console_ tab.

![Model initialized](https://github.com/manugarciaquismondo/MeCoGUI/blob/master/images/mecogui_model_initialized.png)


