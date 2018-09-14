        JMP pre ; start init
n       DEF 000 ; the maximum number
i       DEF 000 ; the current number
tmp     DEF 000 ; the temporary output

pre
        INM n ; get n
        JMP increment ; start increment

increment
        LDA i ; load counter
        INC ; increment counter
        STA i ; save counter
        LDA tmp ; load temporary output
        ADD i ; add current number to temporary output
        STA tmp ; save temporary output
        JMP check ; check if already at last number

check
        LDA i ; load current number
        SUB n ; subtract required number (if < n it will be < 0)
        JMS increment ; if it is < n increment again
        JMP output ; else output

output
        OUT tmp ; output temporary output
        JMP post ; end programm

post
        END ; well - end it, I guess
