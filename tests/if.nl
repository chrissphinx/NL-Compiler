{*********************************************************
  SIMPLE CONTROL FLOW TEST USING IF STATEMENTS
*********************************************************}

PROGRAM Ifs;
  VAR a, b: INTEGER;

BEGIN

  a := 0;
  b := a - 1;

  IF a THEN
    WRITE(1)
  ELSE
    WRITE(0);

  WRITE('a = ');
  WRITE(a);
  WRITE('b = ');
  WRITE(b);

  IF b THEN
    IF a THEN
      WRITE(0)
    ELSE
      WRITE(1)
  ELSE
    WRITE(0)
END
