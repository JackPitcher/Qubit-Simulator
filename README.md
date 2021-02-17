# A Quantum Particle Simulator

## Description of Project

Quantum simulators abound the web, and are an excellent learning resource. 
Students are able to see the abstract mathematical concepts of a *wave function*
or a *unitary matrix transformation* come to life before their eyes, enabling a
firmer grasp on the underlying concepts. Although there are many quantum simulators,
I have yet to find one that simply returns the desired quantum numbers
and allows the user to create a qubit with an arbitrary number of states.
This would be very useful for someone working in Quantum Mechanics to perform
the calculations that can get rather ugly at times, and it is this sort of system
that I wish to create.

This project is of particular interest to me because, as a Physics student who only 
recently took a quantum mechanics course, this is the kind of simulation I wished
had existed so I could get the bigger picture of how the mathematical formulas
represent anything in real life. In addition, this project will allow users
to get a better idea of how quantum computing works, because they will be able
to see how quantum particles, or qubits, can encode information and how this can
be used by measuring the qubits and giving an output value. As quantum computing
is the area of Physics and Computer Science I am most fascinated by, this project
will be a great learning tool for me as well.

## Instructions for User

- You can generate the first required event by clicking the "Add a New State" button
in the top left corner, and then inputting two complex numbers in the two panels
that appear next in the form "x + yi". Note that if you have a complex and an imaginary
part, you must have the "+" sign; if you want the imaginary part to be negative,
write "x + -yi". Note also that the qubit must remained normalized, so the states
will change when you add or remove states; however the ratios of the states squared
will stay the same.

- You can generate the second required event by clicking the "Remove a State" button
just beside the "Add a State" button. You will then enter a complex number in the
same way, and if the number is in your qubit, it will be removed.

- You will see an image of a Bloch Sphere in the top left hand corner for my visual
component. Bloch spheres are common representations of qubits in a way that is
both physical and mathematically valid, as well as visually appealing and easy
to understand. Any state is effectively a vector in the Bloch sphere.

- You can save the qubit by clicking "Save" in the top right hand corner and
writing the name of the file.

- You can load a qubit by either clicking "Load" in the top right hand corner or
on start up of the app.

- You can input a quantum gate with which to measure the qubit with by clicking
"Update Gate" on the right. Note that the gate that is right above this button
is in the general form of a Hermitian matrix; any gate you enter must be in
this form. Therefore the inputs are labeled "A", "B", and "C" for the different
valid inputs.

- You can transform the qubit with the gate by clicking the "Transform Qubit" button.
This will multiply each state of the qubit with the gate.

- You can measure the qubit by clicking the "Measure Qubit" button and it will return a state with
a random probability. Note that the qubit will collapse upon measurement,
meaning that the measured state will be the only state in the qubit.

- You can measure the qubit in a particular basis by clicking the "Measure Qubit with Gate" button
 and find the probability of getting
either the positive or negative eigenvalue. Note that you are not actually
measuring the qubit so the qubit will not collapse here.

- You can find the expectation value of a qubit by clicking the "Measure Qubit's expectation value with Gate"
button. This is simply the average value you would return if you measured the qubit an
arbitrary number of times.
