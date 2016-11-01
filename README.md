# MeCoGUI

MeCoGUI is a minimalistic Graphic User Interface (GUI) for the modeling and simulation of P-Lingua models using pLinguaCore. MeCoGUI provides controls to parse, simulate and export P system specifications.

To run MeCoGUI, download the file __jar/MeCoGUI.jar__. Then, type in a command prompt console:

> java -jar MeCoGUI.jar

After this, the following interface must appear:

![MeCoGUI main screen](images/mecogui_main.png)

To load a P-Lingua model, click on _File_>_Load Model_ and select the P-Lingua specification to simulate.

![Load P-Lingua model](images/mecogui_load_model.png)

Then, click on _File_>_Load Data_ and select the parameters of the model in _.CSV_ format. 

![Load model parameters](images/mecogui_load_data.png)

The parameters file must contain a _steps_per_cycle_ parameter indicating how many steps compose a simulation cycle.

![Steps per cycle](images/steps_per_cycle.png)

Optionally, a report file with simulation statistics can also be generated clicking on _File_>_Set Report_. Thsi file will be generated during the simulations.

![Set report file](images/mecogui_set_report.png)

The routes of the selected files will appear in MeCoGUI.

![MeCoGUI route display](images/mecogui_routes_set.png)

Once the _Model_ and _Data_ file routes have been set, the P system can be parsed clicking on _Initialize_.

![Initialize model](images/mecogui_initialize.png)

If the parsing process produced no errors, the parsed model should appear on the _Info console_ tab. Otherwise, errors will appear on the _Error console_ tab. Likewise, any warnings will appear on the _Warning console_ tab.

![Model initialized](images/mecogui_model_initialized.png)

All consoles can be cleared by clicking on the _Clear consoles_ button.

![Clear consoles](images/mecogui_clear_consoles.png)

By default, MeCoGUI selects the default simulator for the model indicated by pLinguaCore. A different simulation can be selected by clicking on the _Simulators_ button.

![Select simulator](images/mecogui_set_simulator.png)

Also, a machine-readable specification of the P system can be generated. This is intended for external simulators not integrated in pLinguaCore. For this, click _Output formats_ and select the desired format for the specification.

![Select output format](images/mecogui_output_formats.png)

To simulate the system using the selected pLinguaCore simulator, use the following buttons:

* _Take step_ runs a single simulation step.
* _Simulate_ runs as many simulation cycles as indicated in the field _Number of cycles_. Each simulation cycle consists of as many steps as indicated in the field _Steps per cycle_.
* _Simulate all_ runs as many simulations as indicated in the field _Number of simulations_. Each simulation consists of as many simulation cycles as indicated in the field _Number of cycles_.

![Simulation buttons](images/mecogui_simulation_buttons.png)

During simulation, the current and previous configurations of the system are displayed in the _Info console_ tab.

![Simulation information](images/mecogui_simulation_information.png)

During simulation or when the simulation is finished, the simulation results can be saved in CSV format on the selected file clicking on _File_>_Save Results_.

![Save simulation results](images/mecogui_save_results.png)

The button _Translate results_ will enable processing of simulation result files in future releases. It is currently in alpha version.

This software is available under the GNU General Public License (GPL) version 3.0.
<div align="center"/><img src ="images/gplv3-127x51.png"/></div>
