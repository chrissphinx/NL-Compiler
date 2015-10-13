{*********************************************************
  SIMPLE ARITHMETIC TEST
*********************************************************}

PROGRAM Name;
  VAR x, y: INTEGER;

BEGIN
  READ(x);
  x := x AND x;
  y := x + 5;
  WRITE(y)
END
