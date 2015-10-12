.data
.string .s_nop ""
.text
.frame main 8
loadI -4 => %vr5
add %vr0 %vr5 => %vr6
loadI 1 => %vr7
store %vr7 => %vr6
.L0: nop
loadI -4 => %vr5
loadAO %vr0 %vr5 => %vr8
loadI 10 => %vr9
comp %vr8 %vr9 => %vr10
testle %vr10 => %vr11
loadI 0 => %vr12
comp %vr11 %vr12 => %vr13
testeq %vr13 => %vr14
cbr %vr14 => .L1
loadI -4 => %vr5
loadAO %vr0 %vr5 => %vr8
iwrite %vr8
loadI -4 => %vr5
add %vr0 %vr5 => %vr6
loadI -4 => %vr5
loadAO %vr0 %vr5 => %vr8
loadI 1 => %vr7
add %vr8 %vr7 => %vr15
store %vr15 => %vr6
jumpI .L0
.L1: nop
loadI -4 => %vr5
loadAO %vr0 %vr5 => %vr8
iwrite %vr8
loadI -8 => %vr16
add %vr0 %vr16 => %vr17
loadI 1 => %vr7
store %vr7 => %vr17
.L2: nop
loadI -8 => %vr16
loadAO %vr0 %vr16 => %vr18
loadI 10 => %vr9
comp %vr18 %vr9 => %vr19
testle %vr19 => %vr20
loadI 0 => %vr12
comp %vr20 %vr12 => %vr21
testeq %vr21 => %vr22
cbr %vr22 => .L3
loadI -4 => %vr5
add %vr0 %vr5 => %vr6
loadI 1 => %vr7
store %vr7 => %vr6
.L3: nop
loadI -4 => %vr5
loadAO %vr0 %vr5 => %vr8
loadI 10 => %vr9
comp %vr8 %vr9 => %vr10
testle %vr10 => %vr11
loadI 0 => %vr12
comp %vr11 %vr12 => %vr13
testeq %vr13 => %vr14
cbr %vr14 => .L4
loadI -4 => %vr5
loadAO %vr0 %vr5 => %vr8
loadI -8 => %vr16
loadAO %vr0 %vr16 => %vr18
mult %vr8 %vr18 => %vr23
iwrite %vr23
loadI -4 => %vr5
add %vr0 %vr5 => %vr6
loadI -4 => %vr5
loadAO %vr0 %vr5 => %vr8
loadI 1 => %vr7
add %vr8 %vr7 => %vr15
store %vr15 => %vr6
jumpI .L3
.L4: nop
loadI -8 => %vr16
add %vr0 %vr16 => %vr17
loadI -8 => %vr16
loadAO %vr0 %vr16 => %vr18
loadI 1 => %vr7
add %vr18 %vr7 => %vr24
store %vr24 => %vr17
jumpI .L2
.L5: nop
ret
