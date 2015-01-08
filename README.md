# jTableGenerator
generates tables

## Latex

<pre><code>String columns = "scenario,time,distance";
String row1 = "ref,1000,12";
FluentLatexTableBuilder table = new FluentLatexTableBuilder();
String latexTable = table.withCaption("this is a latex table")
	.withLabel("tab:latex table")
    .withColumns(Splitter.on(',').trimResults().split(columns))
    .withRow(Splitter.on(',').trimResults().split(row1))
    .withRow(Arrays.asList("cordon", "2000", "123"))
    .build();
</code></pre>

which creates a String containing the latex table pretty much like this

<pre><code>\begin{table}
	\begin{center}
		\caption{this is the first table}
		\begin{tabular}{l | r | r}
			\hline
			scenario & time & distance \\
			\hline
			ref & 1000 & 12 \\
			cordon & 2000 & 123 \\
			\hline
		\end{tabular}
	\end{center}
	\label{tab:first table}
\end{table}
</code></pre>
