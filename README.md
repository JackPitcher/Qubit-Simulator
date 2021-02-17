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

## User Stories

- As a user, I want to be able to create a qubit with an arbitrary number of states.
I want the qubit to remain normalized so that the probabilities squared add up to 1 even after adding
more states. I also want to be able to remove states from the qubit.
- As a user, I want to be able to measure a qubit and get an
output (one of the states the particle is in) with a probability dependent on the
scalings of the states.
- As a user, I want to be able to measure a system in a particular basis
(i.e. in the x axis) and find the probability that the qubit will be in
a particular eigenstate when actually measured.
- As a user, I want to be able to measure the expectation value in a particular basis,
or the average value I could expect if I measured the particle
many times in that basis.
- As a user, I want to be able to save a qubit after I quit the app.
- As a user, I want to be able to load a saved qubit when I start the app.

## Extra User Stories
- As a user, I want to be able to transform any particle by passing it through
a unitary transformation matrix.

## Instructions for Grader

-You can generate the first required event by clicking the "Add a New State" button
in the top left corner, and then inputting two complex numbers in the two panels
that appear next in the form "x + yi". Note that if you have a complex and an imaginary
part, you must have the "+" sign; if you want the imaginary part to be negative,
write "x + -yi". Note also that the qubit must remained normalized, so the states
will change when you add or remove states; however the ratios of the states squared
will stay the same.

-You can generate the second required event by clicking the "Remove a State" button
just beside the "Add a State" button. You will then enter a complex number in the
same way, and if the number is in your qubit, it will be removed.

-You will see an image of a Bloch Sphere in the top left hand corner for my visual
component. Bloch spheres are common representations of qubits in a way that is
both physical and mathematically valid, as well as visually appealing and easy
to understand. Any state is effectively a vector in the Bloch sphere.

-You can save the qubit by clicking "Save" in the top right hand corner and
writing the name of the file.

-You can load a qubit by either clicking "Load" in the top right hand corner or
on start up of the app.

-You can input a quantum gate with which to measure the qubit with by clicking
"Update Gate" on the right. Note that the gate that is right above this button
is in the general form of a Hermitian matrix; any gate you enter must be in
this form. Therefore the inputs are labeled "A", "B", and "C" for the different
valid inputs.

- You can transform the qubit with the gate by clicking the "Transform Qubit" button.
This will multiply each stae of the qubit with the gate.

-You can measure the qubit by clicking the "Measure Qubit" button and it will return a state with
a random probability. Note that the qubit will collapse upon measurement,
meaning that the measured state will be the only state in the qubit.

-You can measure the qubit in a particular basis by clicking the "Measure Qubit with Gate" button
 and find the probability of getting
either the positive or negative eigenvalue. Note that you are not actually
measuring the qubit so the qubit will not collapse here.

-You can find the expectation value of a qubit by clicking the "Measure Qubit's expectation value with Gate"
button. This is simply the average value you would return if you measured the qubit an
arbitrary number of times.

## Phase 4: Task 2

I made the Qubit Class robust by throwing an exception when the user tries to remove
a state that is not present. Since Qubit must have at least 1 State (the only time
the Qubit() constructor with no arguments is called is when a qubit is being loaded),
the other methods do not have REQUIREs clauses and thus do not need to throw exceptions.

## Phase 4: Task 3

I identified two problems in terms of there being too much cohesion in the project:
1. First, QubitApp is a very large class that does a lot of different logic relating
to the GUI. This could be separated into different classes. I have added 3 classes
to accomplish this: GateGUI, QubitGUI, and TextAreaGUI, each one being concerned
with a different part of the GUI.
2. Second, I implemented the iterator pattern in the Qubit class to reduce coupling.
Now, if I want to change the way States are stored (e.g. I want to stop the user from
inputting multiples of the same State by using a Set) I can now do so without having
to change anything in my UI package.
3. Third (as a bonus), there are three methods in the Complex class having to do with converting
the complex number to and from string form. This could be done in another class,
since a Complex class should only really have to do with Complex numbers rather than
strings and parsing strings. I have made a new Class, called ComplexString, which
contains all of this logic.