import { useRouteError } from "react-router-dom";

export default function Error() {
	const error = useRouteError();
	console.log(error);

	return (
		<div>
			<h1>Page not found (error component)</h1>
			<p>{error.data}</p>
		</div>
	);
}
