        JMP pre ; start init
a       DEF 000 ; the first number
b       DEF 000 ; the second number
i       DEF 000 ; counter
tmp     DEF 000 ; temporary output

pre
        INM a ; get a
        INM b ; get b
        JMP increment ; start increment

increment
        LDA tmp ; load temporary output
        ADD a ; add a to it
        STA tmp ; save temporary output
        JMP count ; update counter

count
        LDA i ; load counter
        INC ; increment counter
        STA i ; save counter
        JMP check ; check if counter is zero

check
        LDA i ; load counter
        SUB a ; subtract a to be able to jump
        JNP increment ; if <= 0 increment again
        JMP output ; else output and end

output
        OUT tmp ; output temporary output
        JMP post ; end

post
        END ; well - end it, I guess
