        JMP pre ; start init
a       DEF 000 ; the number to subtract
b       DEF 000 ; times a should be subtracted
c       DEF 000 ; the number a will be subtracted from

pre
        INM a ; get a
        INM b ; get b
        JMP subtract ; subtract

subtract
        LDA c ; load number to subtract from
        SUB a ; subtract a
        STA c ; save subtracted number
        JMP decrement ; decrement b

decrement
        LDA b ; load b
        DEC ; decrement b
        STA b ; save b
        JMP check ; check if already enough

check
        LDA b ; load b
        JPL subtract ; if > 0 subtract again
        JMP output ; else output c and end

output
        OUT c ; output c
        JMP post ; end

post
        END ; well - end it, I guess
