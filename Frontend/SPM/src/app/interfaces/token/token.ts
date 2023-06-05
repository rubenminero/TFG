export interface Token {
  id: number;
  token: string;
  tokenType: String;
  expired: Boolean;
  revoked: Boolean;
}
