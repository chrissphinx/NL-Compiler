.data
.string .s_nop ""
.text
.frame main 8
loadI -4 => %vr5
add %vr0 %vr5 => %vr6
loadI 0 => %vr7
store %vr7 => %vr6
loadI -8 => %vr8
add %vr0 %vr8 => %vr9
loadI -4 => %vr5
loadAO %vr0 %vr5 => %vr10
loadI 1 => %vr11
sub %vr10 %vr11 => %vr12
store %vr12 => %vr9
loadI -4 => %vr5
loadAO %vr0 %vr5 => %vr10
cbrne %vr10 => .L0
loadI 1 => %vr11
iwrite %vr11
.L0: nop
loadI 0 => %vr7
iwrite %vr7
