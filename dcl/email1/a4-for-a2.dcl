        JMP init

a       DEF 000
b       DEF 000
c       DEF 000

init
        INM a
        INM b
        JMP prep

prep
        PSHM c ; first pushed param is always last
        PSHM b
        PSHM a ; last pushed param is always '2'
        JSR routine
        POP ; remove 'a' from stack
        POP ; remove 'b' from stack
        POPM c ; remove 'result' from stack and store in 'c'
        JMP post ; output

routine
        inputA EQUAL 2 ; define our first input
        inputB EQUAL 3 ; define our second input
        result EQUAL 4 ; define the address for our result
        JMP loop

loop
        LDAS result
        ADDS inputB
        STAS result
        LDAS inputA
        DEC
        STAS inputA
        JNZ loop
        RTN

post
        OUT c
        END
