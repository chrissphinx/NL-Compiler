.data
.string .s_nop ""
.text
.frame main 8
loadI -4 => %vr5
add %vr0 %vr5 => %vr6
iread %vr6
loadI -4 => %vr5
add %vr0 %vr5 => %vr6
loadI -4 => %vr5
loadAO %vr0 %vr5 => %vr7
loadI -4 => %vr5
loadAO %vr0 %vr5 => %vr7
and %vr7 %vr7 => %vr8
store %vr8 => %vr6
loadI -8 => %vr9
add %vr0 %vr9 => %vr10
loadI -4 => %vr5
loadAO %vr0 %vr5 => %vr7
loadI 5 => %vr11
add %vr7 %vr11 => %vr12
store %vr12 => %vr10
loadI -8 => %vr9
loadAO %vr0 %vr9 => %vr13
iwrite %vr13
ret
