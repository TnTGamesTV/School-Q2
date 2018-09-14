        JMP pre ; start init
A       DEF 000 ; lower number
B       DEF 000 ; higher number
numbers DEF 000 ; amount of numbers between A and B
i       DEF 001 ; counter
o       DEF 000 ; temporary output

pre
        INM A ; read A
        INM B ; read B
        JMP calculate ; calculate distance

calculate
        LDA B ; load greater number (B)
        SUB A ; subtract lower number (A)
        STA numbers ; store as numbers
        JMP decide ; decide if programm should end

decide
        LDA i ; load counter
        SUB numbers ; remove numbers to be able to jump
        JMS output ; if counter < numbers: output and start again
        JMP post ; end programm

increment
        LDA i ; load counter
        INC ; increment counter
        STA i ; store counter
        JMP decide ; decide if programm should end

output
        LDA i ; load counter
        ADD A ; add lower number to get current value
        STA o ; store into temporary output
        OUT o ; output temporary output
        JMP increment ; increment counter

post
        END ; well - end it, I guess
