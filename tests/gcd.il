.data
.string .s_nop ""
.text
.frame main 8
loadI -4 => %vr5
add %vr0 %vr5 => %vr6
iread %vr6
loadI -8 => %vr7
add %vr0 %vr7 => %vr8
iread %vr8
.L0: nop
or %vredu.wmich.cs.maccreery.ast.ParenthesisNode@7cc355be %vredu.wmich.cs.maccreery.ast.ParenthesisNode@6e8cf4c6 => %vr9
loadI 0 => %vr10
comp %vr9 %vr10 => %vr11
testeq %vr11 => %vr12
cbr %vr12 => .L1
