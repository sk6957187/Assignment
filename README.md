# Assignment
(1) Rule Engine with AST
Limitation Each rule keyword shall be seprated by space

Data storage
for rule: oracle sql
meta data: yml file
rule1 = age < 10
Tree
Node1 (age,<,10)

rule2 = age < 20 AND age > 5
Node1 (age,<,20)
Node2 (age,>,5)
Root = AND
Root->left = Node1
Root->right = Node2

Node1 = true
Node2 = true
root = AND
