# TT-Siegwahrscheinlichkeit
Repository to calculate win probability given a certain distance of TTR points and an optional handicap per set.
The probability per match \( p \) is calculated using this formula:

$$
p = \frac{1}{1 + 10^{-\Delta/150}}
$$

with \(\Delta = \text{TTR}_{\text{player}} - \text{TTR}_{\text{opponent}}\).
For a given probability per match one can derive the probability to win a set. Angenommen, die Wahrscheinlichkeit einen Satz zu gewinnen sei jeden Satz identisch und die Matchwahrscheinlichkeit ergebe sich aus drei Satzspielen, dann ergibt sich:

p_match = sum_{i=3}_{5} (5 über i)*p^i*(1-p)^(5-i)
 = (5 über 3) p^3*(1-p)^2 + (5 über 4) p^4*(1-p) + p^5 = 10 p^3*(p^2-2p+1) + 5*p^4-5*p^5 + p^5
= 6*p^5 - 15p^4 +10*p^3
<=> 0= 6*p^5 - 15p^4 +10*p^3 -p_match

Dies ist ein Polynom 5. Grades, dass sich nach p auflösen lässt. Nun wollen wir aus der Gewinnwahrscheinlichkeit pro Satz die Wahrscheinlichkeit für einen einzelnen Punkt berechnen.
Wenn man davon ausgeht, dass bei jedem Punkt die Wahrscheinlichkeit für einen Punktgewinn gleich hoch ist und damit Aufschlagrecht und Psychologie ignoriert, ergibt sich:

p_satz = sum_{i=0}_{